package com.example.enter_school_management.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enter_school_management.Common.Dto.LogDto;
import com.example.enter_school_management.Entity.Log;
import com.example.enter_school_management.Entity.Student;

import java.sql.Date;
import java.util.List;

public interface LogService extends IService<Log> {
    Long saveLog(LogDto logDto, int status);//存储进出校记录并返回id
    Log getLatestLog(String stuId);//获取某学生最后一次进出校记录
    List<Log> getYesterdayLogByStuId(Date yesterday, String stuId);//根据学生id获取该学生过去一天的进出校记录
    List<Log> getLogByDate(Date yesterday, Integer n);
    Log getndaysbeforLog(String stuId, Integer n);
    List<Student> getndaysnotOut(List<Student> studentList,List<Log> LogList, Integer n);

}
