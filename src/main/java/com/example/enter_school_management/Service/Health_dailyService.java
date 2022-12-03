package com.example.enter_school_management.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enter_school_management.Entity.Health_daily;
import com.example.enter_school_management.Entity.Risky_places;

import java.util.List;

public interface Health_dailyService extends IService<Health_daily> {
    List<Health_daily> getLast7DayHealthDaily(String stu_id);
    boolean HDisSafe(Health_daily health_daily,String schoolCity, List<Risky_places> risky_places);
}
