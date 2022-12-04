package com.example.enter_school_management.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enter_school_management.Common.Dto.LeaveApplicationDto;
import com.example.enter_school_management.Entity.LeaveApplication;

import java.util.List;

public interface LeaveApplicationService extends IService<LeaveApplication> {
    Long saveLA(LeaveApplicationDto leaveApplicationDto);
    List<LeaveApplication> getLAByStatus(int status);
    List<LeaveApplication> getAllLA();
    List<LeaveApplication> getLAByStuId(String stuId);
}
