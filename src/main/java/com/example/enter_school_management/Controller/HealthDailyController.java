package com.example.enter_school_management.Controller;

import com.example.enter_school_management.Common.Dto.HealthDailyDto;
import com.example.enter_school_management.Common.lang.Result;
import com.example.enter_school_management.Entity.HealthDaily;
import com.example.enter_school_management.Entity.RiskyPlaces;
import com.example.enter_school_management.Entity.Student;
import com.example.enter_school_management.Service.HealthDailyService;
import com.example.enter_school_management.Service.RiskyPlacesService;
import com.example.enter_school_management.Service.SchoolService;
import com.example.enter_school_management.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.enter_school_management.Common.lang.Const.HDUpdated;

@RestController
@RequestMapping("/healthdaily")
public class HealthDailyController {
    @Autowired
    HealthDailyService health_dailyService;
    @Autowired
    SchoolService schoolService;
    @Autowired
    StudentService studentService;
    @Autowired
    RiskyPlacesService risky_placesService;

    //学生填写健康日报
    @PostMapping ("/fillin")
    public Result fillInHealthDaily(@RequestBody HealthDailyDto health_dailyDto){
        Student student = studentService.getById(health_dailyDto.getStuId());
        student.setHdUpdated(HDUpdated);
        studentService.updateById(student);
        Long newId = health_dailyService.saveHD(health_dailyDto);
        Long stu_school_id = studentService.getSchoolIdById(health_dailyDto.getStuId());
        String schoolCity = schoolService.getSchoolcityById(stu_school_id);
        List<RiskyPlaces> risky_places = risky_placesService.getAllRP();
        if(!health_dailyService.HDisSafe(newId,schoolCity,risky_places)){
            return Result.fail("警告：当前体温异常或位置与学校不在同一城市或处于风险地区，将于次日关闭入校权限！");
        }
        return Result.succ("健康日报提交成功");
    }

    //获取学生过去n天的健康日报
    @PostMapping("/nDayrecords")
    public Result DaysRecord(@RequestParam String stuId,@RequestParam int days){
        List<HealthDaily> records = health_dailyService.getLastnDayHealthDaily(stuId,days);
        return Result.succ("成功获取过去"+days+"天该生填写健康日报！", records);
    }




}
