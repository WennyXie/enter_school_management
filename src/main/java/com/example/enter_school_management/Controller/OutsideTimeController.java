package com.example.enter_school_management.Controller;

import com.example.enter_school_management.Common.Dto.OuttimeDto;
import com.example.enter_school_management.Common.lang.Const;
import com.example.enter_school_management.Common.lang.Result;
import com.example.enter_school_management.Entity.OutsideTime;
import com.example.enter_school_management.Entity.Student;
import com.example.enter_school_management.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/outside")
public class OutsideTimeController {
    @Autowired
    OutsideTimeService outsideTimeService;
    @Autowired
    StudentService studentService;
    @Autowired
    StuClassService stuClassService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    AdminService adminService;

    //每日12点记录每位同学离校时长
    @Scheduled(cron = "0 0 0 * * ?")
    //@Scheduled(cron = "30 * * * * ?")
    public void dailyUpdateOT(){
        System.out.println("所有同学离校时间已更新");
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

    //辅导员查询该班学生从当天算起过去一年的离校总时长
    @GetMapping("/instr/getLastYearTotalOT")
    public Result instrGetLastYearTotalOT(@RequestParam String adminId){
        List<Student> studentList = studentService.getByClass(stuClassService.getClassByAdmin(adminId).getClassId());
        List<OuttimeDto> classTotalOT = new ArrayList<>();
        for(Student student : studentList){
            List<OutsideTime> outsideTimes = outsideTimeService.getLastnDayOutsideTime(student.getStuId(), Const.oneYear);
            Long totalOT = 0L;
            for(OutsideTime outsideTime : outsideTimes){
                totalOT += outsideTime.getOtTime();
            }
            OuttimeDto outtimeDto = new OuttimeDto(student,totalOT);
            classTotalOT.add(outtimeDto);
        }
        return Result.succ(classTotalOT);
    }

    //院系管理员查询该院学生从当天算起过去一年的离校总时长
    @GetMapping("/DA/getLastYearTotalOT")
    public Result DAGetLastYearTotalOT(@RequestParam String adminId){
        List<Student> studentList = studentService.getByDepart(departmentService.getDepartByAdmin(adminId).getDeptId());
        List<OuttimeDto> classTotalOT = new ArrayList<>();
        for(Student student : studentList){
            List<OutsideTime> outsideTimes = outsideTimeService.getLastnDayOutsideTime(student.getStuId(), Const.oneYear);
            Long totalOT = 0L;
            for(OutsideTime outsideTime : outsideTimes){
                totalOT += outsideTime.getOtTime();
            }
            OuttimeDto outtimeDto = new OuttimeDto(student,totalOT);
            classTotalOT.add(outtimeDto);
        }
        return Result.succ(classTotalOT);
    }
}
