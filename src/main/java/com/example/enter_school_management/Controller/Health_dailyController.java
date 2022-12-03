package com.example.enter_school_management.Controller;

import com.example.enter_school_management.Common.lang.Result;
import com.example.enter_school_management.Entity.Health_daily;
import com.example.enter_school_management.Service.Health_dailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthdaily")
public class Health_dailyController {
    @Autowired
    Health_dailyService health_dailyService;

    @PutMapping("/fillin")
    public Result fillInHealthDaily(Health_daily health_daily)

}
