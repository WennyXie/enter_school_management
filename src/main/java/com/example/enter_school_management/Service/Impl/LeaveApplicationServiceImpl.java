package com.example.enter_school_management.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enter_school_management.Common.Dto.LeaveApplicationDto;
import com.example.enter_school_management.Common.lang.Const;
import com.example.enter_school_management.Common.lang.Result;
import com.example.enter_school_management.Entity.Admin;
import com.example.enter_school_management.Entity.HealthDaily;
import com.example.enter_school_management.Entity.LeaveApplication;
import com.example.enter_school_management.Mapper.AdminMapper;
import com.example.enter_school_management.Mapper.LeaveApplicationMapper;
import com.example.enter_school_management.Service.LeaveApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static com.example.enter_school_management.Common.lang.Const.*;

@Service
public class LeaveApplicationServiceImpl extends ServiceImpl<LeaveApplicationMapper, LeaveApplication> implements LeaveApplicationService {
    @Autowired
    LeaveApplicationMapper leaveApplicationMapper;
    @Autowired
    AdminMapper adminMapper;

    @Override
    public Long saveLA(LeaveApplicationDto leaveApplicationDto){
        LeaveApplication leaveApplication = new LeaveApplication();
        leaveApplication.setLeavAppId(null);
        leaveApplication.setStuId(leaveApplicationDto.getStuId());
        leaveApplication.setLeavReason(leaveApplicationDto.getLeavReason());
        leaveApplication.setDestCity(leaveApplicationDto.getDestCity());
        leaveApplication.setDestDistrict(leaveApplicationDto.getDestDistrict());
        leaveApplication.setDestStreet(leaveApplicationDto.getDestStreet());
        leaveApplication.setExpLeavdate(leaveApplicationDto.getExpLeavdate());
        leaveApplication.setExpRetdate(leaveApplication.getExpRetdate());
        leaveApplication.setStatus(appSubmit);
        leaveApplication.setCurrentAdminId(null);
        leaveApplication.setRejectReason(null);
        long d = System.currentTimeMillis();
        Date currentDate = new Date(d);
        leaveApplication.setCurrentDate(currentDate);
        leaveApplicationMapper.insert(leaveApplication);
        return  leaveApplication.getLeavAppId();
    }

    @Override
    public List<LeaveApplication> getLAByStatus(int status){
        QueryWrapper<LeaveApplication> leaveApplicationQueryWrapper = new QueryWrapper<>();
        leaveApplicationQueryWrapper.eq("status",status);
        return leaveApplicationMapper.selectList(leaveApplicationQueryWrapper);
    }

    @Override
    public List<LeaveApplication> getAllLA(){
        QueryWrapper<LeaveApplication> leaveApplicationQueryWrapper = new QueryWrapper<>();
        leaveApplicationQueryWrapper.isNotNull("leav_app_id");
        return leaveApplicationMapper.selectList(leaveApplicationQueryWrapper);
    }

    @Override
    public List<LeaveApplication> getLAByStuId(String stuId){
        QueryWrapper<LeaveApplication> leaveApplicationQueryWrapper = new QueryWrapper<>();
        leaveApplicationQueryWrapper.eq("stu_id",stuId);
        return leaveApplicationMapper.selectList(leaveApplicationQueryWrapper);
    }

    @Override
    public List<LeaveApplication> getLAByStuIdAndStatus(String stuId, int status){
        QueryWrapper<LeaveApplication> leaveApplicationQueryWrapper = new QueryWrapper<>();
        leaveApplicationQueryWrapper.eq("stu_id",stuId).eq("status",status);
        return leaveApplicationMapper.selectList(leaveApplicationQueryWrapper);
    }

    @Override
    public List<LeaveApplication> getLastNDayUncheckedLA(int days){
        QueryWrapper<LeaveApplication> leaveApplicationQueryWrapper = new QueryWrapper<>();
        java.util.Date utilDate = new java.util.Date();
        Date currentDate = new Date(utilDate.getTime());
        Calendar calendar =new GregorianCalendar();
        calendar.setTime(utilDate);
        calendar.add(Calendar.DATE, -days);
// calendar的time转成java.util.Date格式日期
        utilDate = (java.util.Date)calendar.getTime();
//java.util.Date日期转换成转成java.sql.Date格式
        Date daysBefore =new Date(utilDate.getTime());
        leaveApplicationQueryWrapper.eq("status", appSubmit).between("current_date",daysBefore,currentDate);
        return leaveApplicationMapper.selectList(leaveApplicationQueryWrapper);
    }

    @Override
    public boolean checkTodayIsLeave(String stuId){
        long d = System.currentTimeMillis();
        Date currentDate = new Date(d);
        QueryWrapper<LeaveApplication> leaveApplicationQueryWrapper = new QueryWrapper<>();
        LeaveApplication leaveApplication = getOne(leaveApplicationQueryWrapper.eq("status",Const.appDAApprove)
                                                .orderByDesc("current_date"));
        return currentDate.after(leaveApplication.getExpLeavdate()) && currentDate.before(leaveApplication.getExpRetdate());
    }

    @Override
    public List<LeaveApplication> getLAByDuty(String adminId){
        Admin currentAdmin = adminMapper.selectById(adminId);
        QueryWrapper<LeaveApplication> leaveApplicationQueryWrapper = new QueryWrapper<>();
        if(currentAdmin.getAdminType() == roleInstructor) {
            leaveApplicationQueryWrapper.eq("admin_id", adminId).eq("status", appSubmit);
            return leaveApplicationMapper.selectList(leaveApplicationQueryWrapper);
        }
        else if(currentAdmin.getAdminType() == roleDeptAdmin){
            leaveApplicationQueryWrapper.eq("admin_id", adminId).eq("status", appITApprove);
            return leaveApplicationMapper.selectList(leaveApplicationQueryWrapper);
        }
        else if(currentAdmin.getAdminType() == roleSuperAdmin){
            return getAllLA();
        }
        else return new ArrayList<LeaveApplication>();
    }
}
