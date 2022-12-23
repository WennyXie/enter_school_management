package com.example.enter_school_management.Controller;

import com.example.enter_school_management.Common.Dto.LogDto;
import com.example.enter_school_management.Common.Dto.OutsideStuDto;
import com.example.enter_school_management.Common.Dto.DeptandCampusDto;
import com.example.enter_school_management.Common.lang.Const;
import com.example.enter_school_management.Common.lang.Result;
import com.example.enter_school_management.Entity.*;
import com.example.enter_school_management.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.*;

import static com.example.enter_school_management.Common.lang.Const.inSchool;
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

    @Autowired
    StuClassService stuClassService;

    @Autowired
    CampusService campusService;

    @Autowired
    YesterdayService yesterdayService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    PermissionService permissionService;

    //学生刷卡进出校并进行记录
    @PostMapping("/slashcard")
    public Result slashCard(@RequestBody LogDto logDto){
        Log lastLog = logService.getLatestLog(logDto.getStuId());
        int lastStatus = lastLog.getLogStatus();
        if(lastStatus == inSchool && !Objects.equals(logDto.getCampusId(), lastLog.getCampusId()))
            return  Result.fail("出校校区与该生已在校区不符！");
        List<Permission> permissionList = permissionService.getPermissionByStuid(logDto.getStuId());
        for(Permission permission:permissionList){
            if(Objects.equals(permission.getCampusId(), logDto.getCampusId())){
                if(permission.getPermitStatus() == Const.PermDenied && lastStatus == outSchool){
                    return Result.fail("你没有进入该校区的权限");
                }
            }
        }
        Long logId = logService.saveLog(logDto, 1-lastStatus);
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
            if(lastLog == null){
                OutsideStuDto outsideStuDto = new OutsideStuDto();
                outsideStuDto.setStudent(student);
                outsideStuDto.setLog(lastLog);
                outsideStuDtos.add(outsideStuDto);
            }else{
                if(lastLog.getLogStatus() == outSchool){
                    OutsideStuDto outsideStuDto = new OutsideStuDto();
                    outsideStuDto.setStudent(student);
                    outsideStuDto.setLog(lastLog);
                    outsideStuDtos.add(outsideStuDto);
                }
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
            if(lastLog == null){
                continue;
            }
            if(lastLog.getLogStatus() == outSchool && !leaveApplicationService.checkTodayIsLeave(stuId)
                    && outsideTimeService.LastOutsideTimeOver24h(lastLog)){
                outsideStu.add(student);
            }
        }
        return Result.succ(outsideStu.size(),outsideStu);
    }


    //过去n天出入校最多的校区
    @GetMapping("/mostoutCampus")
    public Result getmostoutCampus(@RequestParam Integer n){
        Date yesterday = yesterdayService.yesterday();
        List<Log> LogList = logService.getLogByDate(yesterday, n);
        List<Department> departmentList = departmentService.getAllDepart();
        System.out.println(LogList);
        List<Campus> campusList = campusService.getAllCampus();
        List<DeptandCampusDto> deptandCampusDtos = new ArrayList<>();
        Long num = 0L;
        for(Department department:departmentList){
            List<Campus> reCampus = new ArrayList<>();
            List<Long> idList = new ArrayList<>();
            List<Long> totalnum = new ArrayList<>();
            List<Student> studentList = studentService.getByDepart(department.getDeptId());
            for(Student student:studentList){
                for(Log log:LogList){
                    if(Objects.equals(log.getStuId(), student.getStuId())){
                        Long campusId = log.getCampusId();
                        idList.add(campusId);
                    }
                }
            }
            for(Campus campus : campusList){
                for(Long id:idList){
                    if(Objects.equals(id, campus.getCampusId())){
                        num = num +1 ;
                    }
                }
                totalnum.add(num);
                num = 0L;
            }
            Long max = Collections.max(totalnum);
            if(max == 0L){
                reCampus=null;
            }else {
                for (int i = 0; i < totalnum.size(); i++) {
                    if (Objects.equals(max, totalnum.get(i))) {
                        reCampus.add(campusList.get(i));
                    }
                }
            }
            DeptandCampusDto deptandCampusDto = new DeptandCampusDto(department,reCampus);
            deptandCampusDtos.add(deptandCampusDto);
        }

        return Result.succ("获取数据成功",deptandCampusDtos);
    }

    //过去n天一直未出校的学生-班级
    @GetMapping("/class/ndaysnotOut")
    public Result getndaysnotoutfoeClass(@RequestParam String adminId,@RequestParam Integer n){
        Date yesterday = yesterdayService.yesterday();
        List<Log> LogList = logService.getLogByDate(yesterday, n);//获取n天内的log记录
        StuClass theClass = stuClassService.getClassByAdmin(adminId);
        List<Student> studentList = studentService.getByClass(theClass.getClassId());
        List<Student> reStudent = logService.getndaysnotOut(studentList,LogList,n);
        return Result.succ("获取数据成功",reStudent);
    }

    //过去n天一直未出校的学生-院系
    @GetMapping("/dept/ndaysnotOut")
    public Result getndaysnotoutfoedept(@RequestParam String adminId,@RequestParam Integer n){
        Date yesterday = yesterdayService.yesterday();
        List<Log> LogList = logService.getLogByDate(yesterday, n);//获取n天内的log记录
        Department department = departmentService.getDepartByAdmin(adminId);
        List<Student> studentList = studentService.getByDepart(department.getDeptId());
        List<Student> reStudent = logService.getndaysnotOut(studentList,LogList,n);
        return Result.succ("获取数据成功",reStudent);
    }

    //过去n天一直未出校的学生-学校
    @GetMapping("/school/ndaysnotOut")
    public Result getndaysnotoutfoedept(@RequestParam Integer n){
        Date yesterday = yesterdayService.yesterday();
        List<Log> LogList = logService.getLogByDate(yesterday, n);//获取n天内的log记录
        List<Student> studentList = studentService.getAllStudent();
        List<Student> reStudent = logService.getndaysnotOut(studentList,LogList,n);
        return Result.succ("获取数据成功",reStudent);
    }

}


