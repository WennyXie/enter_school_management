package com.example.enter_school_management.Controller;

import com.example.enter_school_management.Common.lang.Const;
import com.example.enter_school_management.Common.lang.Result;
import com.example.enter_school_management.Entity.OutsideTime;
import com.example.enter_school_management.Service.OutsideTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //查询学生从当天算起过去一年的离校总时长
    @GetMapping("/getLastYearTotalOT")
    public Result getLastYearTotalOT(@RequestParam String stuId){
        List<OutsideTime> outsideTimes = outsideTimeService.getLastnDayOutsideTime(stuId, Const.oneYear);
        Long totalOT = 0L;
        for(OutsideTime outsideTime : outsideTimes){
            totalOT += outsideTime.getOtTime();
        }
        return Result.succ(totalOT);
    }
}
