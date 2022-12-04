package com.example.enter_school_management.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enter_school_management.Entity.School;
import com.example.enter_school_management.Mapper.SchoolMapper;
import com.example.enter_school_management.Service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, School> implements SchoolService {
    @Autowired
    SchoolMapper schoolMapper;

    @Override
    public String getSchoolcityById(Long schoolId){
        School school = getOne(new QueryWrapper<School>().eq("school_id",schoolId));
        return school.getSchoolCity();
    }
}
