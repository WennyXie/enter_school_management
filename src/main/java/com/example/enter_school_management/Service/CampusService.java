package com.example.enter_school_management.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enter_school_management.Entity.Campus;

import java.util.List;

public interface CampusService extends IService<Campus> {
    List<Campus> getAllCampus();
}
