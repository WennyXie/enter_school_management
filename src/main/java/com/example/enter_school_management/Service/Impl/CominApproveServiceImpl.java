package com.example.enter_school_management.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enter_school_management.Common.Dto.CominApproveDto;
import com.example.enter_school_management.Common.lang.Result;
import com.example.enter_school_management.Entity.CominApprove;
import com.example.enter_school_management.Entity.LeaveApplication;
import com.example.enter_school_management.Mapper.CominApproveMapper;
import com.example.enter_school_management.Service.CominApproveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
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

    @Override
    public List<CominApprove> getCAByStuIdAndStatus(String stuId, int status){
        QueryWrapper<CominApprove> cominApproveQueryWrapper = new QueryWrapper<>();
        cominApproveQueryWrapper.eq("stu_id",stuId).eq("status",status);
        return cominApproveMapper.selectList(cominApproveQueryWrapper);
    }

    @Override
    public List<CominApprove> getLastNDayUncheckedLA(int days){
        QueryWrapper<CominApprove> cominApproveQueryWrapper = new QueryWrapper<>();
        java.util.Date utilDate = new java.util.Date();
        Date currentDate = new Date(utilDate.getTime());
        Calendar calendar =new GregorianCalendar();
        calendar.setTime(utilDate);
        calendar.add(Calendar.DATE, -days);
// calendar的time转成java.util.Date格式日期
        utilDate = (java.util.Date)calendar.getTime();
//java.util.Date日期转换成转成java.sql.Date格式
        Date daysBefore =new Date(utilDate.getTime());
        cominApproveQueryWrapper.eq("status", appSubmit).between("current_date",daysBefore,currentDate);
        return cominApproveMapper.selectList(cominApproveQueryWrapper);
    }

}
