package com.example.enter_school_management.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enter_school_management.Entity.OutsideTime;

public interface OutsideTimeService extends IService<OutsideTime> {
    void dailyUpdateOutsideTime();
}
