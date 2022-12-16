package com.example.enter_school_management.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enter_school_management.Common.Dto.CominApproveDto;
import com.example.enter_school_management.Entity.CominApprove;
import com.example.enter_school_management.Entity.LeaveApplication;

import java.util.List;

public interface CominApproveService extends IService<CominApprove> {
    Long saveCA(CominApproveDto cominApproveDto);//存储入校申请并返回id
    List<CominApprove> getCAByStatus(int status);//根据申请状态查询入校申请
    List<CominApprove> getAllCA();//获取所有入校申请
    List<CominApprove> getCAByStuId(String stuId);//根据学号查询入校申请
    List<CominApprove> getCAByStuIdAndStatus(String stuId, int status);//根据学号和申请状态获取返校申请
    List<CominApprove> getLastNDayUncheckedLA(int days);//获取过去n天未审核返校申请
}
