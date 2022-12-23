package com.example.enter_school_management.Controller;

import com.example.enter_school_management.Common.Dto.OuttimeDto;
import com.example.enter_school_management.Common.lang.Const;
import com.example.enter_school_management.Common.lang.Result;
import com.example.enter_school_management.Entity.Department;
import com.example.enter_school_management.Entity.OutsideTime;
import com.example.enter_school_management.Entity.StuClass;
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
        String stotalOT = outsideTimeService.convertMillis(totalOT);
        System.out.println(stotalOT);
        return Result.succ("获取书库成功",stotalOT);
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
            String stotalOT=outsideTimeService.convertMillis(totalOT);
            OuttimeDto outtimeDto = new OuttimeDto(student,stotalOT);
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
            String stotalOT = outsideTimeService.convertMillis(totalOT);
            OuttimeDto outtimeDto = new OuttimeDto(student,stotalOT);
            classTotalOT.add(outtimeDto);
        }
        return Result.succ(classTotalOT);
    }

    //前n个平均离校时间最长的学生
    @GetMapping("/class/longestOuttime")
    public Result getclasslongestOut(@RequestParam String adminId,@RequestParam Integer n){
        StuClass theClass = stuClassService.getClassByAdmin(adminId);
        List<Student> studentList = studentService.getByClass(theClass.getClassId());
        if(studentList.size() == 0){
            return Result.fail("该班级没有学生");
        }
        List<OuttimeDto> reOuttimeList = outsideTimeService.getnlogestOuttime(studentList,n);
        return Result.succ("获取数据成功",reOuttimeList);
    }

    @GetMapping("/dept/longestOuttime")
    public Result getdeptlongestOut(@RequestParam String adminId,@RequestParam Integer n){
        Department department = departmentService.getDepartByAdmin(adminId);
        List<Student> studentList = studentService.getByDepart(department.getDeptId());
        if(studentList.size() == 0){
            return Result.fail("该院系没有学生");
        }
        List<OuttimeDto> reOuttimeList = outsideTimeService.getnlogestOuttime(studentList,n);
        if(reOuttimeList == null){
            return Result.fail("该院系学生没有离校时间记录");
        }
        return Result.succ("获取数据成功",reOuttimeList);
    }

    @GetMapping("/school/longestOuttime")
    public Result getschoollongestOut(@RequestParam Integer n){
        List<Student> studentList = studentService.getAllStudent();
        if(studentList.size() == 0){
            return Result.fail("该校没有学生");
        }
        List<OuttimeDto> reOuttimeList = outsideTimeService.getnlogestOuttime(studentList,n);
        if(reOuttimeList == null){
            return Result.fail("该院系学生没有离校时间记录");
        }
        return Result.succ("获取数据成功",reOuttimeList);
    }


}
