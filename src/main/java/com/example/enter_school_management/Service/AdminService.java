package com.example.enter_school_management.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enter_school_management.Entity.Admin;

public interface AdminService extends IService<Admin> {
    Admin getAdminById(String adminId);
}
