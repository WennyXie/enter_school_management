package com.example.enter_school_management.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enter_school_management.Entity.Permission;

import java.util.List;

public interface PermissionService extends IService<Permission> {
    void dailyUpdatePermission();//每日更新每个同学的入校权限
    List<Permission> getPermissionByStuid(String stuId);//根据学号获取所有校区入校权限
    List<Permission> getPermissionByCampusId(Long campusId);//根据校区获取所有同学的入校权限
}
