package com.example.enter_school_management.Controller;

import com.example.enter_school_management.Common.Dto.PermDto;
import com.example.enter_school_management.Common.lang.Const;
import com.example.enter_school_management.Common.lang.Result;
import com.example.enter_school_management.Entity.*;
import com.example.enter_school_management.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/perm")
public class PermissionController {
    @Autowired
    PermissionService permissionService;
    @Autowired
    StudentService studentService;
    @Autowired
    CampusService campusService;
    @Autowired
    StuClassService stuClassService;
    @Autowired
    DepartmentService departmentService;

    @Autowired
    AdminService adminService;


    //每日五点更新每位同学的进校权限
    @Scheduled(cron = "0 0 0 * * ?")
    void updatePerm(){
        permissionService.dailyUpdatePermission();
    }

    //学生查看自己的入校权限
    @GetMapping("/stu/getperm")
    public Result getPerm(@RequestParam String stuId){
        List<Permission> permissions = permissionService.getPermissionByStuid(stuId);
        return Result.succ(permissions);
    }

    //学生查看本班入校权限的数据
    @GetMapping("/stu/getstatistic")
    public Result getStatistic(@RequestParam String stuId){
        List<Student> studentList = studentService.getByClass(studentService.getById(stuId).getStuClassId());
        int stuNum = studentList.size();
        List<Integer> permNum = new ArrayList<>();
        List<Campus> campuses = campusService.getAllCampus();
        //根据校区的数量建好list
        for(int i = 0; i < campuses.size();i++){
            permNum.add(0);
        }
        //遍历学生列表中每一个学生的权限列表，如果对a校区有进校权限，则按顺序给对应的校区权限人数加1
        for(Student student : studentList){
            List<Permission> permissions = permissionService.getPermissionByStuid(student.getStuId());
            for(Permission p : permissions){
                if(p.getPermitStatus() == Const.PermApproved){
                    int index = (p.getCampusId()).intValue()-1;
                    permNum.set(index,permNum.get(index)+1);
                }
            }
        }
        return Result.succ("学生总数和各校区有权限进入的人数已返回",stuNum,permNum);
    }

    //辅导员查看本班学生入校权限
    @GetMapping("/instr/getClass")
    public Result getClass(@RequestParam String adminId){
        List<Student> studentList = studentService.getByClass(stuClassService.getClassByAdmin(adminId).getClassId());
        List<PermDto> permDtos = new ArrayList<>();
        //同时返回学生信息和权限信息
        for(Student student : studentList){
            List<Permission> permissions = permissionService.getPermissionByStuid(student.getStuId());
            PermDto permDto = new PermDto(student,permissions);
            permDtos.add(permDto);
        }
        return Result.succ(permDtos);
    }

    //辅导员查看同院系别班的入校权限的数据
    @GetMapping("/instr/getstatistic")
    public Result instrGetStatistic(@RequestParam String adminId){
        //先找出该院系中的所有班级
        List<StuClass> stuClasses = stuClassService.getClassByDept(stuClassService.getClassByAdmin(adminId).getDeptId());
        List<Admin> adminList = new ArrayList<>();
        //建立每个班级人数的list
        List<Integer> totalStudentNum = new ArrayList<>();
        //建立分班级每位同学入校权限的list
        List<List<Integer>> classPermNum = new ArrayList<>();
        for(StuClass stuClass: stuClasses){
            List<Student> studentList = studentService.getByClass(stuClass.getClassId());
            adminList.add(adminService.getById(stuClass.getInstructorId()));
            totalStudentNum.add(studentList.size());
            List<Integer> permNum = new ArrayList<>();
            List<Campus> campuses = campusService.getAllCampus();
            for(int i = 0; i < campuses.size();i++){
                permNum.add(0);
            }
            for(Student student : studentList){
                List<Permission> permissions = permissionService.getPermissionByStuid(student.getStuId());
                for(Permission p : permissions){
                    if(p.getPermitStatus() == Const.PermApproved){
                        int index = (p.getCampusId()).intValue()-1;
                        permNum.set(index,permNum.get(index)+1);
                    }
                }
            }
            classPermNum.add(permNum);
        }
        return Result.succ("学生总数和各校区有权限进入的人数已返回",stuClasses,adminList,totalStudentNum,classPermNum);
    }

    //院系管理员查看本学院生入校权限
    @GetMapping("/DA/getDept")
    public Result getDept(@RequestParam String adminId){
        List<Student> studentList = studentService.getByDepart(departmentService.getDepartByAdmin(adminId).getDeptId());
        List<PermDto> permDtos = new ArrayList<>();
        for(Student student : studentList){
            List<Permission> permissions = permissionService.getPermissionByStuid(student.getStuId());
            PermDto permDto = new PermDto(student,permissions);
            permDtos.add(permDto);
        }
        return Result.succ(permDtos);
    }

}
