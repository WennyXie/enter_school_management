package com.example.enter_school_management.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enter_school_management.Entity.School;

public interface SchoolService extends IService<School> {
    String getSchoolcityById(Long schoolId);//根据学校id获取学校所在城市
}
