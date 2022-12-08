package com.example.enter_school_management.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enter_school_management.Common.Dto.CominApproveDto;
import com.example.enter_school_management.Entity.CominApprove;
import com.example.enter_school_management.Mapper.CominApproveMapper;
import com.example.enter_school_management.Service.CominApproveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

import static com.example.enter_school_management.Common.lang.Const.appSubmit;

@Service
public class CominApproveServiceImpl extends ServiceImpl<CominApproveMapper, CominApprove> implements CominApproveService {
    @Autowired
    CominApproveMapper cominApproveMapper;

    @Override
    public Long saveCA(CominApproveDto cominApproveDto){
        CominApprove cominApprove = new CominApprove();
        cominApprove.setCominAppId(null);
        cominApprove.setStuId(cominApproveDto.getStuId());
        cominApprove.setCominReason(cominApproveDto.getCominReason());
        cominApprove.setExpComindate(cominApproveDto.getExpComindate());
        cominApprove.setCurrentAdminId(null);
        cominApprove.setRejectReason(null);
        cominApprove.setStatus(appSubmit);
        long d = System.currentTimeMillis();
        Date currentDate = new Date(d);
        cominApprove.setCurrentDate(currentDate);
        cominApproveMapper.insert(cominApprove);
        return cominApprove.getCominAppId();
    }

    @Override
    public List<CominApprove> getCAByStatus(int status){
        QueryWrapper<CominApprove> cominApproveQueryWrapper = new QueryWrapper<>();
        cominApproveQueryWrapper.eq("status",status);
        return cominApproveMapper.selectList(cominApproveQueryWrapper);
    }

    @Override
    public List<CominApprove> getAllCA(){
        QueryWrapper<CominApprove> cominApproveQueryWrapper = new QueryWrapper<>();
        cominApproveQueryWrapper.isNotNull("comin_app_id");
        return cominApproveMapper.selectList(cominApproveQueryWrapper);
    }

    @Override
    public List<CominApprove> getCAByStuId(String stuId){
        QueryWrapper<CominApprove> cominApproveQueryWrapper = new QueryWrapper<>();
        cominApproveQueryWrapper.eq("stu_id",stuId);
        return cominApproveMapper.selectList(cominApproveQueryWrapper);
    }
}
