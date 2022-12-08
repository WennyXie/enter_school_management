package com.example.enter_school_management.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enter_school_management.Entity.HealthDaily;
import com.example.enter_school_management.Entity.LeaveApplication;
import com.example.enter_school_management.Entity.Permission;
import com.example.enter_school_management.Entity.Student;
import com.example.enter_school_management.Mapper.PermissionMapper;
import com.example.enter_school_management.Mapper.StudentMapper;
import com.example.enter_school_management.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static com.example.enter_school_management.Common.lang.Const.*;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Autowired
    PermissionMapper permissionMapper;
    @Autowired
    HealthDailyService healthDailyService;
    @Autowired
    StudentService studentService;
    @Autowired
    LeaveApplicationService leaveApplicationService;
    @Autowired
    YesterdayService yesterdayService;

    @Override
    public void dailyUpdatePermission(){
        //修改健康日报有风险的人的进校权限
        List<HealthDaily> yesterdayHD = healthDailyService.getYesterDayUnsafeHealthDaily();
        for(HealthDaily HD : yesterdayHD){
            List<Permission> permissions = getPermissionByStuid(HD.getStuId());
            for(Permission p: permissions){
                p.setPermitStatus(PermDenied);
                permissionMapper.updateById(p);
            }
        }

        //修改未填报健康日报的人的进校权限
        List<Student> notUpdatedStudents = studentService.getStudentByHDStatus(HDNotUpdated);
        for(Student s : notUpdatedStudents){
            List<Permission> permissions = getPermissionByStuid(s.getStuId());
            for(Permission p: permissions){
                p.setPermitStatus(PermDenied);
                permissionMapper.updateById(p);
            }
        }

        //修改离校申请通过同学的进校权限
        List<LeaveApplication> leaveApplicationsApproved = leaveApplicationService.getLAByStatus(appDAApprove);
        for(LeaveApplication LA : leaveApplicationsApproved){
            Date expLeaveDate = LA.getExpLeavdate();
            Date yesterday =yesterdayService.yesterday();
            if(yesterday == expLeaveDate){
                List<Permission> permissions = getPermissionByStuid(LA.getStuId());
                for(Permission p: permissions){
                    p.setPermitStatus(PermDenied);
                    permissionMapper.updateById(p);
                }
            }
        }
    }

    @Override
    public List<Permission> getPermissionByStuid(String stuId){
        QueryWrapper<Permission> permissionQueryWrapper = new QueryWrapper<>();
        permissionQueryWrapper.eq("stu_id",stuId);
        return permissionMapper.selectList(permissionQueryWrapper);
    }

    @Override
    public List<Permission> getPermissionByCampusId(Long campusId){
        QueryWrapper<Permission> permissionQueryWrapper = new QueryWrapper<>();
        permissionQueryWrapper.eq("campus_id",campusId);
        return permissionMapper.selectList(permissionQueryWrapper);
    }
}
