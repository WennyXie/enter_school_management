package com.example.enter_school_management.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enter_school_management.Common.Dto.LeaveApplicationDto;
import com.example.enter_school_management.Entity.LeaveApplication;
import com.example.enter_school_management.Mapper.LeaveApplicationMapper;
import com.example.enter_school_management.Service.LeaveApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

import static com.example.enter_school_management.Common.lang.Const.appSubmit;

@Service
public class LeaveApplicationServiceImpl extends ServiceImpl<LeaveApplicationMapper, LeaveApplication> implements LeaveApplicationService {
    @Autowired
    LeaveApplicationMapper leaveApplicationMapper;

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
}
