package com.example.enter_school_management.Service;

import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
@Service
public class YesterdayService {
    //获取昨日日期
    public Date yesterday(){
        java.util.Date utilDate = new java.util.Date();
        Calendar calendar =new GregorianCalendar();
        calendar.setTime(utilDate);
        calendar.add(Calendar.DATE, -1);
        utilDate = (java.util.Date)calendar.getTime();
        Date yesterday =new Date(utilDate.getTime());
        return yesterday;
    }

    public boolean isYesterday(Date today, Date yesterday){
        Calendar calendar =new GregorianCalendar();
        calendar.setTime(today);
        calendar.add(Calendar.DATE, -1);
        java.util.Date utilDate = (java.util.Date)calendar.getTime();
        Date trueYesterday = new Date(utilDate.getTime());
        return trueYesterday.equals(yesterday);
    }
}
