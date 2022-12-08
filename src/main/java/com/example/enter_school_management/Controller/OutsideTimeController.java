package com.example.enter_school_management.Controller;

import com.example.enter_school_management.Service.OutsideTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/outside")
public class OutsideTimeController {
    @Autowired
    OutsideTimeService outsideTimeService;

    @GetMapping("")
    public void dailyUpdateOT(){
        outsideTimeService.dailyUpdateOutsideTime();
    }
}
