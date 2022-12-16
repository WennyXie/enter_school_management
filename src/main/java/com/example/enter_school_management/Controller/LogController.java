package com.example.enter_school_management.Controller;

import com.example.enter_school_management.Common.Dto.LogDto;
import com.example.enter_school_management.Common.Dto.OutsideStuDto;
import com.example.enter_school_management.Common.lang.Result;
import com.example.enter_school_management.Entity.Log;
import com.example.enter_school_management.Entity.Student;
import com.example.enter_school_management.Service.LeaveApplicationService;
import com.example.enter_school_management.Service.LogService;
import com.example.enter_school_management.Service.OutsideTimeService;
import com.example.enter_school_management.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.enter_school_management.Common.lang.Const.outSchool;

@RestController
@RequestMapping("/log")
public class LogController {
    @Autowired
    LogService logService;
    @Autowired
    StudentService studentService;
    @Autowired
    LeaveApplicationService leaveApplicationService;
    @Autowired
    OutsideTimeService outsideTimeService;

    //学生刷卡进出校并进行记录
    @PostMapping("/slashcard")
    public Result slashCard(@RequestBody LogDto logDto){
        Long logId = logService.saveLog(logDto);
        if(logId == -1) return  Result.fail("出校校区与该生已在校区不符！");
        Log log = logService.getById(logId);
        if(log.getLogStatus() == outSchool){
            return Result.succ("出校成功！");
        }
        return Result.succ("进校成功！");
    }

    //离校状态的学生数量、个人信息和各自离校时间
    @GetMapping("/alloutschoolstudents")
    public Result allOutschoolStudents(){
        List<Student> studentList = studentService.getAllStudent();
        List<OutsideStuDto> outsideStuDtos = new ArrayList<>();
        for (Student student : studentList){
            Log lastLog = logService.getLatestLog(student.getStuId());
            if(lastLog.getLogStatus() == outSchool){
                OutsideStuDto outsideStuDto = new OutsideStuDto();
                outsideStuDto.setStudent(student);
                outsideStuDto.setLog(lastLog);
                outsideStuDtos.add(outsideStuDto);
            }
        }
        return Result.succ(outsideStuDtos.size(),outsideStuDtos);
    }

    //未提交离校申请但离校超过24h的学生数量
    @GetMapping("/outschoolover24h")
    public Result outschoolOver24h(){
        List<Student> studentList = studentService.getAllStudent();
        List<Student> outsideStu = new ArrayList<>();
        for (Student student : studentList){
            String stuId = student.getStuId();
            Log lastLog = logService.getLatestLog(stuId);
            if(lastLog.getLogStatus() == outSchool && !leaveApplicationService.checkTodayIsLeave(stuId)
                    && outsideTimeService.LastOutsideTimeOver24h(lastLog)){
                outsideStu.add(student);
            }
        }
        return Result.succ(outsideStu.size(),outsideStu);
    }
}
