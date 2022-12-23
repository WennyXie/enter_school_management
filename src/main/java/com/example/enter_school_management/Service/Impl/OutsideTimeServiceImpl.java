package com.example.enter_school_management.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enter_school_management.Entity.HealthDaily;
import com.example.enter_school_management.Entity.Log;
import com.example.enter_school_management.Entity.OutsideTime;
import com.example.enter_school_management.Common.Dto.OuttimeDto;
import com.example.enter_school_management.Entity.Student;
import com.example.enter_school_management.Mapper.OutsideTimeMapper;
import com.example.enter_school_management.Service.LogService;
import com.example.enter_school_management.Service.OutsideTimeService;
import com.example.enter_school_management.Service.StudentService;
import com.example.enter_school_management.Service.YesterdayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

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

    @Override
    public List<OutsideTime> getLastnDayOutsideTime(String stuId, int days){
        QueryWrapper<OutsideTime> outsideTimeQueryWrapper = new QueryWrapper<>();
        java.util.Date utilDate = new java.util.Date();
        Date currentDate = new Date(utilDate.getTime());
        Calendar calendar =new GregorianCalendar();
        calendar.setTime(utilDate);
        calendar.add(Calendar.DATE, -days);
// calendar的time转成java.util.Date格式日期
        utilDate = (java.util.Date)calendar.getTime();
//java.util.Date日期转换成转成java.sql.Date格式
        Date daysBefore =new Date(utilDate.getTime());
        outsideTimeQueryWrapper.eq("stu_id", stuId).between("date",daysBefore,currentDate);
        return outsideTimeMapper.selectList(outsideTimeQueryWrapper);
    }

    @Override
    public boolean LastOutsideTimeOver24h(Log lastlog){
        long nd = 1000 * 24 * 60 * 60;
        java.util.Date date = new java.util.Date();
        java.util.Date lastLogDate = new java.util.Date();
        long time = lastlog.getLogDate().getTime() + lastlog.getLogTime().getTime();
        lastLogDate.setTime(time);
        long diff = date.getTime()-lastLogDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        return day >= 1;
    }

    @Override
    public List<OutsideTime> getOutsideTime(String stuId){
        QueryWrapper<OutsideTime> outsideTimeQueryWrapper = new QueryWrapper<>();
        outsideTimeQueryWrapper.eq("stu_id", stuId);
        return outsideTimeMapper.selectList(outsideTimeQueryWrapper);
    }

    @Override
    public List<OuttimeDto> getnlogestOuttime(List<Student> studentList,Integer n){
        long totalTime = 0L;
        List<OuttimeDto> outtimeDtoList = new ArrayList<>();
        for(Student student:studentList){
            String stuId = student.getStuId();
            List<OutsideTime> outsideTimes = getOutsideTime(stuId);
            if(outsideTimes.size() == 0){
                continue;
            }
            for(OutsideTime outsideTime:outsideTimes){
                totalTime = totalTime + outsideTime.getOtTime();
            }
            String stotalTime=convertMillis(totalTime);
            OuttimeDto outtimeDto = new OuttimeDto(student,stotalTime);
            outtimeDtoList.add(outtimeDto);
            totalTime = 0L;
        }
        if(outtimeDtoList.size() == 0){
            return null;
        }
        if(outtimeDtoList.size() < n){
            n=outtimeDtoList.size();
        }
        List<OuttimeDto> reOuttimeList = new ArrayList<>();
        for(int i=0;i<n;i++){
            OuttimeDto maxoutTimeDto = outtimeDtoList.stream().max(Comparator.comparing(OuttimeDto::getTotalOT)).get();
            reOuttimeList.add(maxoutTimeDto);
            outtimeDtoList.remove(maxoutTimeDto);
        }
        return reOuttimeList;
    }

    @Override
    public String convertMillis (long duration){
        duration = duration/1000;
        if (duration !=  0) {
            long hour = duration/ 3600;
            long minute = (duration % 3600) / 60;
            long second = (duration % 3600) % 60;

            String hourStr = hour == 0 ? "00" : hour > 10 ? hour + "" : "0" + hour;
            String minuteStr = minute == 0 ? "00" : minute > 10 ? minute + "" : "0" + minute;
            String secondStr = second == 0 ? "00" : second > 10 ? second + "" : "0" + second;

            return hourStr + "小时" + minuteStr + "分" + secondStr+"秒";
        }
        return null;
    }
}
