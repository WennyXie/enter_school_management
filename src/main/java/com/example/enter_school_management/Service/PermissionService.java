package com.example.enter_school_management.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enter_school_management.Entity.Permission;

import java.util.List;

public interface PermissionService extends IService<Permission> {
    void dailyUpdatePermission();
    List<Permission> getPermissionByStuid(String stuId);
    List<Permission> getPermissionByCampusId(Long campusId);
}
