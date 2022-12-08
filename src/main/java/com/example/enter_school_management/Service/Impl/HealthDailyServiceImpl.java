package com.example.enter_school_management.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enter_school_management.Common.Dto.HealthDailyDto;
import com.example.enter_school_management.Entity.HealthDaily;
import com.example.enter_school_management.Entity.RiskyPlaces;
import com.example.enter_school_management.Mapper.HealthDailyMapper;
import com.example.enter_school_management.Service.HealthDailyService;
import com.example.enter_school_management.Service.YesterdayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

import static com.example.enter_school_management.Common.lang.Const.*;

@Service
public class HealthDailyServiceImpl extends ServiceImpl<HealthDailyMapper, HealthDaily> implements HealthDailyService {
    @Autowired
    HealthDailyMapper health_dailyMapper;
    @Autowired
    YesterdayService yesterdayService;

    @Override
    public List<HealthDaily> getLastnDayHealthDaily(String stuId, int days){
        QueryWrapper<HealthDaily> healthDailyQueryWrapper = new QueryWrapper<>();
        java.util.Date utilDate = new java.util.Date();
        Date currentDate = new Date(utilDate.getTime());
        Calendar calendar =new GregorianCalendar();
        calendar.setTime(utilDate);
        calendar.add(Calendar.DATE, -days);
// calendar的time转成java.util.Date格式日期
        utilDate = (java.util.Date)calendar.getTime();
//java.util.Date日期转换成转成java.sql.Date格式
        Date daysBefore =new Date(utilDate.getTime());
        healthDailyQueryWrapper.eq("stu_id", stuId).between("date",daysBefore,currentDate);
        return health_dailyMapper.selectList(healthDailyQueryWrapper);
    }

    @Override
    public List<HealthDaily> getYesterDayUnsafeHealthDaily(){
        QueryWrapper<HealthDaily> healthDailyQueryWrapper = new QueryWrapper<>();
        Date yesterday =yesterdayService.yesterday();
        healthDailyQueryWrapper.eq("hd_status",HDUnsafe).eq("date",yesterday);
        return health_dailyMapper.selectList(healthDailyQueryWrapper);
    }

    @Override
    public boolean HDisSafe(Long HDid,String schoolCity, List<RiskyPlaces> riskyPlaces){
        HealthDaily healthDaily = getById(HDid);
        if(healthDaily.getBodyTemperature() > sickTemperature){
            healthDaily.setHdStatus(HDUnsafe);
            health_dailyMapper.updateById(healthDaily);
            return false;
        }
        String currentCity = healthDaily.getCity();
        String currentDistrict = healthDaily.getDistrict();
        String currentStreet = healthDaily.getStreet();
        if(!Objects.equals(currentCity, schoolCity)){
            healthDaily.setHdStatus(HDUnsafe);
            health_dailyMapper.updateById(healthDaily);
            return false;
        }
        for(RiskyPlaces rp:riskyPlaces){
            if(Objects.equals(currentCity, rp.getCity()) && Objects.equals(currentDistrict, rp.getDistrict())
                    && Objects.equals(currentStreet, rp.getStreet())){
                healthDaily.setHdStatus(HDUnsafe);
                health_dailyMapper.updateById(healthDaily);
                return false;
            }
        }
        healthDaily.setHdStatus(HDSafe);
        health_dailyMapper.updateById(healthDaily);
        return true;
    }

    @Override
    public Long saveHD(HealthDailyDto healthDailyDto){
        HealthDaily healthDaily = new HealthDaily();
        healthDaily.setHdId(null);
        healthDaily.setStuId(healthDailyDto.getStuId());
        healthDaily.setBodyTemperature(healthDailyDto.getBodyTemperature());
        healthDaily.setCity(healthDailyDto.getCity());
        healthDaily.setDistrict(healthDailyDto.getDistrict());
        healthDaily.setStreet(healthDailyDto.getStreet());
        healthDaily.setHdStatus(null);
        long d = System.currentTimeMillis();
        Date currentDate = new Date(d);
        Time currentTime = new Time(d);
        healthDaily.setDate(currentDate);
        healthDaily.setTime(currentTime);
        health_dailyMapper.insert(healthDaily);
        return healthDaily.getHdId();
    }


}
