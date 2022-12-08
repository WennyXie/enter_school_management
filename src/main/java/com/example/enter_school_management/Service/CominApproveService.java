package com.example.enter_school_management.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enter_school_management.Common.Dto.CominApproveDto;
import com.example.enter_school_management.Entity.CominApprove;

import java.util.List;

public interface CominApproveService extends IService<CominApprove> {
    Long saveCA(CominApproveDto cominApproveDto);
    List<CominApprove> getCAByStatus(int status);
    List<CominApprove> getAllCA();
    List<CominApprove> getCAByStuId(String stuId);
}
