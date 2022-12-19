package com.example.enter_school_management.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enter_school_management.Common.Dto.HealthDailyDto;
import com.example.enter_school_management.Entity.HealthDaily;
import com.example.enter_school_management.Entity.RiskyPlaces;

import java.util.List;

public interface HealthDailyService extends IService<HealthDaily> {
    List<HealthDaily> getLastnDayHealthDaily(String stuId, int days);//取某学生最后n天的健康日报
    List<HealthDaily> getYesterDayUnsafeHealthDaily();//取出所有昨天异常的健康日报
    boolean HDisSafe(Long HDid,String schoolCity, List<RiskyPlaces> riskyPlaces);//检查健康日报是否正常
    Long saveHD(HealthDailyDto healthDailyDto);//存储健康日报并返回Id
    List<HealthDaily> getDuplicateTime(String stuId);
}
