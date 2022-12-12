package com.example.enter_school_management.Controller;

import com.example.enter_school_management.Service.OutsideTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/outside")
public class OutsideTimeController {
    @Autowired
    OutsideTimeService outsideTimeService;

    //每日12点记录每位同学离校时长
    @Scheduled(cron = "0 0 0 * * ?")
    public void dailyUpdateOT(){
        outsideTimeService.dailyUpdateOutsideTime();
    }
}
