package com.example.enter_school_management.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enter_school_management.Common.Dto.HealthDailyDto;
import com.example.enter_school_management.Entity.HealthDaily;
import com.example.enter_school_management.Entity.RiskyPlaces;

import java.util.List;

public interface HealthDailyService extends IService<HealthDaily> {
    List<HealthDaily> getLastnDayHealthDaily(String stuId, int days);
    List<HealthDaily> getYesterDayUnsafeHealthDaily();
    boolean HDisSafe(Long HDid,String schoolCity, List<RiskyPlaces> riskyPlaces);
    Long saveHD(HealthDailyDto healthDailyDto);
}
