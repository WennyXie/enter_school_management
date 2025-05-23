package com.example.enter_school_management.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enter_school_management.Entity.Department;
import com.example.enter_school_management.Entity.StuClass;

import java.util.List;

public interface DepartmentService extends IService<Department> {
    Department getDepartByAdmin(String adminId);
    List<Department> getAllDepart();
}
