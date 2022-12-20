package com.example.enter_school_management.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enter_school_management.Common.Dto.CominApproveDto;
import com.example.enter_school_management.Common.lang.Result;
import com.example.enter_school_management.Entity.Admin;
import com.example.enter_school_management.Entity.CominApprove;
import com.example.enter_school_management.Entity.LeaveApplication;
import com.example.enter_school_management.Mapper.AdminMapper;
import com.example.enter_school_management.Mapper.CominApproveMapper;
import com.example.enter_school_management.Service.CominApproveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static com.example.enter_school_management.Common.lang.Const.*;

@Service
public class CominApproveServiceImpl extends ServiceImpl<CominApproveMapper, CominApprove> implements CominApproveService {
    @Autowired
    CominApproveMapper cominApproveMapper;
    @Autowired
    AdminMapper adminMapper;

    @Override
    public Long saveCA(CominApproveDto cominApproveDto){
        CominApprove cominApprove = new CominApprove();
        cominApprove.setCominAppId(null);
        cominApprove.setStuId(cominApproveDto.getStuId());
        cominApprove.setCominReason(cominApproveDto.getCominReason());
        cominApprove.setExpComindate(cominApproveDto.getExpComindate());
        cominApprove.setCurrentAdminId(null);
        cominApprove.setRejectReason(null);
        cominApprove.setAppStatus(appSubmit);
        cominApprove.setAdminId(cominApproveDto.getAdminId());
        long d = System.currentTimeMillis();
        java.sql.Date currentDate = new java.sql.Date(d);
        cominApprove.setMyDate(currentDate);
        System.out.println(cominApprove);
        cominApproveMapper.insert(cominApprove);
        return cominApprove.getCominAppId();
    }

    @Override
    public List<CominApprove> getCAByStatus(int status){
        QueryWrapper<CominApprove> cominApproveQueryWrapper = new QueryWrapper<>();
        cominApproveQueryWrapper.eq("app_status",status);
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
        cominApproveQueryWrapper.eq("stu_id",stuId).eq("app_status",status);
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
        cominApproveQueryWrapper.eq("app_status", appSubmit).between("my_date",daysBefore,currentDate);
        return cominApproveMapper.selectList(cominApproveQueryWrapper);
    }

    @Override
    public List<CominApprove> getCAByDuty(String adminId){
        Admin currentAdmin = adminMapper.selectById(adminId);
        QueryWrapper<CominApprove> cominApproveQueryWrapper = new QueryWrapper<>();
        if(currentAdmin.getAdminType() == roleInstructor) {
            cominApproveQueryWrapper.eq("admin_id", adminId).eq("app_status", appSubmit);
            return cominApproveMapper.selectList(cominApproveQueryWrapper);
        }
        else if(currentAdmin.getAdminType() == roleDeptAdmin){
            cominApproveQueryWrapper.eq("admin_id", adminId).eq("app_status", appITApprove);
            return cominApproveMapper.selectList(cominApproveQueryWrapper);
        }
        else if(currentAdmin.getAdminType() == roleSuperAdmin){
            return getAllCA();
        }
        else return new ArrayList<CominApprove>();
    }

}
