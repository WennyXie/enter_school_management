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

import java.sql.Timestamp;
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
        if(lastStatus == inSchool && !Objects.equals(logDto.getCampusId(), lastLog.getLogId()))
            return (long) -1;
        log.setCampusId(logDto.getCampusId());
        log.setLogStatus(1-lastLog.getLogStatus());
        Student student = studentService.getStudentById(logDto.getStuId());
        log.setDeptId(stuClassService.getById(student.getStuClassId()).getDeptId());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        log.setLogDatetime(timestamp);
        logMapper.insert(log);
        return log.getLogId();
    }

    @Override
    public Log getLatestLog(String stuId){
        Log lastLog = getOne(new QueryWrapper<Log>().eq("stu_id",stuId)
                .orderByDesc("log_datetime").last("limit 1"));
        return lastLog;
    }
}
