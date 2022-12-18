package com.example.enter_school_management.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enter_school_management.Common.Dto.LogDto;
import com.example.enter_school_management.Entity.Log;
import com.example.enter_school_management.Entity.Student;
import com.example.enter_school_management.Mapper.LogMapper;
import com.example.enter_school_management.Service.LogService;
import com.example.enter_school_management.Service.StuClassService;
import com.example.enter_school_management.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import static com.example.enter_school_management.Common.lang.Const.inSchool;

@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {
    @Autowired
    LogMapper logMapper;
    @Autowired
    StudentService studentService;
    @Autowired
    StuClassService stuClassService;

    @Override
    public Long saveLog(LogDto logDto){
        Log log = new Log();
        log.setLogId(null);
        log.setStuId(logDto.getStuId());
        Log lastLog = getLatestLog(logDto.getStuId());
        int lastStatus = lastLog.getLogStatus();
        if(lastStatus == inSchool && !Objects.equals(logDto.getCampusId(), lastLog.getCampusId()))
            return (long) -1;
        log.setCampusId(logDto.getCampusId());
        log.setLogStatus(1-lastLog.getLogStatus());
        Student student = studentService.getById(logDto.getStuId());
        log.setDeptId(stuClassService.getById(student.getStuClassId()).getDeptId());
        long d = System.currentTimeMillis();
        Date currentDate = new Date(d);
        Time currentTime = new Time(d);
        log.setLogDate(currentDate);
        log.setLogTime(currentTime);
        logMapper.insert(log);
        return log.getLogId();
    }

    @Override
    public Log getLatestLog(String stuId){
        Log lastLog = getOne(new QueryWrapper<Log>().eq("stu_id",stuId)
                .orderByDesc("log_date").orderByDesc("log_time").last("limit 1"));
        return lastLog;
    }

    @Override
    public List<Log> getYesterdayLogByStuId(Date yesterday, String stuId){
        QueryWrapper<Log> logQueryWrapper = new QueryWrapper<>();
        logQueryWrapper.eq("log_date",yesterday).eq("stu_id",stuId).orderByAsc("log_time");
        return logMapper.selectList(logQueryWrapper);
    }
}
