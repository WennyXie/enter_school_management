package com.example.enter_school_management.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enter_school_management.Entity.Campus;
import com.example.enter_school_management.Mapper.CampusMapper;
import com.example.enter_school_management.Service.CampusService;
import org.springframework.stereotype.Service;

@Service
public class CampusServiceImpl extends ServiceImpl<CampusMapper, Campus> implements CampusService {
}
