package com.example.enter_school_management.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enter_school_management.Entity.Log;
import com.example.enter_school_management.Entity.OutsideTime;
import com.example.enter_school_management.Common.Dto.OuttimeDto;
import com.example.enter_school_management.Entity.Student;

import java.util.List;

public interface OutsideTimeService extends IService<OutsideTime> {
    void dailyUpdateOutsideTime();//每日存储每位学生的离校总时长
    List<OutsideTime> getLastnDayOutsideTime(String stuId, int days);
    boolean LastOutsideTimeOver24h(Log lastlog);
    List<OutsideTime> getOutsideTime(String stuId);
    List<OuttimeDto> getnlogestOuttime(List<Student> studentList, Integer n);
    String convertMillis (long duration);

}
