package com.example.enter_school_management.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enter_school_management.Entity.Campus;
import com.example.enter_school_management.Mapper.CampusMapper;
import com.example.enter_school_management.Service.CampusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampusServiceImpl extends ServiceImpl<CampusMapper, Campus> implements CampusService {
    @Autowired
    CampusMapper campusMapper;

    @Override
    public List<Campus> getAllCampus(){
        QueryWrapper<Campus> campusQueryWrapper = new QueryWrapper<>();
        campusQueryWrapper.isNotNull("campus_id");
        return campusMapper.selectList(campusQueryWrapper);
    }
}
