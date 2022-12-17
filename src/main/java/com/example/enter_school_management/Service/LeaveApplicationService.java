package com.example.enter_school_management.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enter_school_management.Common.Dto.LeaveApplicationDto;
import com.example.enter_school_management.Entity.LeaveApplication;

import java.util.List;

public interface LeaveApplicationService extends IService<LeaveApplication> {
    Long saveLA(LeaveApplicationDto leaveApplicationDto);//存储离校申请并返回id
    List<LeaveApplication> getLAByStatus(int status);//根据申请状态获取所有离校申请
    List<LeaveApplication> getLAByDuty(String adminId);//根据申请状态获取所有离校申请
    List<LeaveApplication> getAllLA();//获取所有离校申请
    List<LeaveApplication> getLAByStuId(String stuId);//根据学号获取离校申请
    List<LeaveApplication> getLAByStuIdAndStatus(String stuId, int status);//根据学号和申请状态获取离校申请
    List<LeaveApplication> getLastNDayUncheckedLA(int days);//获取过去n天未审核离校申请
    boolean checkTodayIsLeave(String stuId);
}
