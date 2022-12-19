package com.example.enter_school_management.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enter_school_management.Entity.StuClass;

import java.util.List;

public interface StuClassService extends IService<StuClass> {
    StuClass getClassByAdmin(String adminId);
    List<StuClass> getClassByDept(Long deptId);
}
