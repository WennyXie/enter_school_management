package com.example.enter_school_management.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enter_school_management.Entity.Log;
import com.example.enter_school_management.Entity.OutsideTime;
import com.example.enter_school_management.Entity.Student;
import com.example.enter_school_management.Mapper.OutsideTimeMapper;
import com.example.enter_school_management.Service.LogService;
import com.example.enter_school_management.Service.OutsideTimeService;
import com.example.enter_school_management.Service.StudentService;
import com.example.enter_school_management.Service.YesterdayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import static com.example.enter_school_management.Common.lang.Const.*;

@Service
public class OutsideTimeServiceImpl extends ServiceImpl<OutsideTimeMapper, OutsideTime> implements OutsideTimeService {
    @Autowired
    OutsideTimeMapper outsideTimeMapper;
    @Autowired
    LogService logService;
    @Autowired
    StudentService studentService;
    @Autowired
    YesterdayService yesterdayService;

    @Override
    public void dailyUpdateOutsideTime(){
        List<Student> studentList = studentService.getAllStudent();
        Date yesterday = yesterdayService.yesterday();
        for(Student s : studentList){
            OutsideTime outsideTime = new OutsideTime();
            outsideTime.setStuId(s.getStuId());
            outsideTime.setDate(yesterday);
            outsideTime.setOtId(null);
            List<Log> yesterdayLog = logService.getYesterdayLogByStuId(yesterday,s.getStuId());
            if(yesterdayLog.size() == 0){
                Log lastLog = logService.getLatestLog(s.getStuId());
                if(lastLog.getLogStatus() == outSchool)
                    outsideTime.setOtTime(allDayOutsideSchool);
                else outsideTime.setOtTime(allDayInsideSchool);
                save(outsideTime);
                continue;
            }
            LocalTime from = null;
            LocalTime to = null;
            Long totalDurationMillis = 0L;
            Duration between;
            int flag = 0;
            if(yesterdayLog.get(0).getLogStatus() == outSchool) {
                flag = firstOutschool;
                from = yesterdayLog.get(0).getLogTime().toLocalTime();
            }
            else {
                from = LocalTime.of(0, 0, 0, 0);
                flag = firstInschool;
            }
            for(int i = 0; i < yesterdayLog.size(); i++){
                if(flag == firstOutschool && i == 0) {
                    if(++i == yesterdayLog.size()) break;
                }
                to = yesterdayLog.get(i).getLogTime().toLocalTime();
                between = Duration.between(from, to);
                totalDurationMillis += between.toMillis();
                if(++i == yesterdayLog.size()){
                    flag = lastInschool;
                    break;
                }
                from = yesterdayLog.get(i).getLogTime().toLocalTime();
            }
            if(flag != lastInschool || to == null){
                to = LocalTime.of(23, 59, 59, 0);
                between = Duration.between(from, to);
                totalDurationMillis += between.toMillis();
            }
            outsideTime.setOtTime(totalDurationMillis);
            save(outsideTime);
        }
    }
}
