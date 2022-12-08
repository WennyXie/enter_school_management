package com.example.enter_school_management.Service;

import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
@Service
public class YesterdayService {
    public Date yesterday(){
        java.util.Date utilDate = new java.util.Date();
        Calendar calendar =new GregorianCalendar();
        calendar.setTime(utilDate);
        calendar.add(Calendar.DATE, -1);
        utilDate = (java.util.Date)calendar.getTime();
        Date yesterday =new Date(utilDate.getTime());
        return yesterday;
    }
}
