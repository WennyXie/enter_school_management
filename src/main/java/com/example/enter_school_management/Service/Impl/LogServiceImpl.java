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
import java.util.*;

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
    public Long saveLog(LogDto logDto, int status){
        Log log = new Log();
        log.setLogId(null);
        log.setStuId(logDto.getStuId());
        log.setCampusId(logDto.getCampusId());
        log.setLogStatus(status);
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

    @Override
    public List<Log> getLogByDate(Date yesterday, Integer n){
        QueryWrapper<Log> logQueryWrapper = new QueryWrapper<>();
        java.util.Date utilDate = new java.util.Date();
        Calendar calendar =new GregorianCalendar();
        calendar.setTime(utilDate);
        calendar.add(Calendar.DATE, -n);
        utilDate = (java.util.Date)calendar.getTime();
        Date ndaysbefor =new Date(utilDate.getTime());
        System.out.println(ndaysbefor);
        logQueryWrapper.between("log_date",ndaysbefor,yesterday);
        return logMapper.selectList(logQueryWrapper);
    }

    @Override
    public Log getndaysbeforLog(String stuId, Integer n){
        QueryWrapper<Log> logQueryWrapper = new QueryWrapper<>();
        java.util.Date utilDate = new java.util.Date();
        Calendar calendar =new GregorianCalendar();
        calendar.setTime(utilDate);
        calendar.add(Calendar.DATE, -(n+1));
        utilDate = (java.util.Date)calendar.getTime();
        Date ndaysbefore =new Date(utilDate.getTime());
        System.out.println(ndaysbefore);
        logQueryWrapper.eq("log_date",ndaysbefore).eq("stu_id",stuId).orderByAsc("log_time");
        List<Log> allLog = logMapper.selectList(logQueryWrapper);
        System.out.println(allLog);
        int index = allLog.size();
        if(index == 0){
            return null;
        }
        return allLog.get(index-1);
    }

    @Override
    public List<Student> getndaysnotOut(List<Student> studentList, List<Log> LogList, Integer n){
        List<Log> LastLogList = new ArrayList<>();
        for(Student student:studentList){
            Log lastlog = getLatestLog(student.getStuId());
            if(lastlog == null){
                continue;
            }
            LastLogList.add(lastlog);
        }
        int flag = 0;
        java.util.Date utilDate = new java.util.Date();
        Calendar calendar =new GregorianCalendar();
        calendar.setTime(utilDate);
        calendar.add(Calendar.DATE, -(n+1));
        utilDate = (java.util.Date)calendar.getTime();
        Date nbeforedate =new Date(utilDate.getTime());
        System.out.println("lastlog");
        System.out.println(LastLogList);
        List<Student> reStudent = new ArrayList<>();
        for(Log log:LastLogList){
            Date lastdate = log.getLogDate();
            if(lastdate.before(nbeforedate)){
                if(log.getLogStatus() == inSchool){
                    reStudent.add(studentService.getStudentById(log.getStuId()));
                }
            }
        }
        System.out.println("res");
        System.out.println(reStudent);
        return reStudent;
    }
}
