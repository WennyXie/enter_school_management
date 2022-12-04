package com.example.enter_school_management.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enter_school_management.Entity.StuClass;
import com.example.enter_school_management.Mapper.StuClassMapper;
import com.example.enter_school_management.Service.StuClassService;
import org.springframework.stereotype.Service;

@Service
public class StuClassServiceImpl extends ServiceImpl<StuClassMapper, StuClass> implements StuClassService {
}
