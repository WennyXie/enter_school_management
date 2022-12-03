package com.example.enter_school_management.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enter_school_management.Entity.Health_daily;
import com.example.enter_school_management.Entity.Risky_places;
import com.example.enter_school_management.Mapper.Health_dailyMapper;
import com.example.enter_school_management.Service.Health_dailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

import static com.example.enter_school_management.Common.lang.Const.HDSafe;
import static com.example.enter_school_management.Common.lang.Const.HDUnsafe;

@Service
public class Health_dailyServiceImpl extends ServiceImpl<Health_dailyMapper, Health_daily> implements Health_dailyService {
    @Autowired
    Health_dailyMapper health_dailyMapper;

    @Override
    public List<Health_daily> getLast7DayHealthDaily(String stu_id){
        QueryWrapper<Health_daily> healthDailyQueryWrapper = new QueryWrapper<>();
        java.util.Date utilDate = new java.util.Date();
        Date currentDate = new Date(utilDate.getTime());
        Calendar calendar =new GregorianCalendar();
        calendar.setTime(utilDate);
        calendar.add(Calendar.DATE, -7);
// calendar的time转成java.util.Date格式日期
        utilDate = (java.util.Date)calendar.getTime();
//java.util.Date日期转换成转成java.sql.Date格式
        Date daysBefore =new Date(utilDate.getTime());
        healthDailyQueryWrapper.eq("stu_id",stu_id).between("date",daysBefore,currentDate);
        return health_dailyMapper.selectList(healthDailyQueryWrapper);
    }

    @Override
    public boolean HDisSafe(Health_daily health_daily,String schoolCity, List<Risky_places> risky_places){
        String currentCity = health_daily.getCity();
        String currentDistrict = health_daily.getDistrict();
        String currentStreet = health_daily.getStreet();
        if(!Objects.equals(currentCity, schoolCity)){
            health_daily.setHd_status(HDUnsafe);
            return false;
        }
        for(Risky_places rp:risky_places){
            if(Objects.equals(currentCity, rp.getCity()) && Objects.equals(currentDistrict, rp.getDistrict())
                    && Objects.equals(currentStreet, rp.getStreet()))
                health_daily.setHd_status(HDUnsafe);
            return false;
        }
        health_daily.setHd_status(HDSafe);
        return true;
    }


}
