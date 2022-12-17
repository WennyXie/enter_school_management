package com.example.enter_school_management.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enter_school_management.Entity.Department;
import com.example.enter_school_management.Entity.StuClass;
import com.example.enter_school_management.Mapper.DepartmentMapper;
import com.example.enter_school_management.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    public Department getDepartByAdmin(String adminId){
        QueryWrapper<Department> departmentQueryWrapper = new QueryWrapper<>();
        return getOne(departmentQueryWrapper.eq("dept_admin_id",adminId));
    }
}
