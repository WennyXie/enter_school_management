package com.example.enter_school_management.Controller;

import com.example.enter_school_management.Common.lang.Result;
import com.example.enter_school_management.Common.Dto.UserInfoDto;
import com.example.enter_school_management.Entity.Student;
import com.example.enter_school_management.Entity.Admin;
import com.example.enter_school_management.Entity.StuClass;
import com.example.enter_school_management.Entity.Department;
import com.example.enter_school_management.Service.StudentService;
import com.example.enter_school_management.Service.AdminService;
import com.example.enter_school_management.Service.StuClassService;
import com.example.enter_school_management.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/UserInfo")
public class UserInfoController {
    @Autowired
    StudentService StudentService;

    @Autowired
    AdminService AdminService;

    @Autowired
    StuClassService StuClassService;

    @Autowired
    DepartmentService  DepartmentService;

    //获取学生的信息
    @GetMapping("/student")
    public Result getStuentInfo(@RequestParam String stuId){
        System.out.println(stuId);
        Student Student = StudentService.getStudentById(stuId);
        StuClass stuClass = StuClassService.getById(Student.getStuClassId());
        String stuClassName = stuClass.getClassName();
        Admin admin = AdminService.getById(stuClass.getInstructorId());
        String adminName = admin.getAdminName();
        String adminId = admin.getAdminId();
        Department department = DepartmentService.getById(Student.getStuDepartId());
        String stuDepartName = department.getDeptName();
        UserInfoDto userInfoDto = new UserInfoDto(Student,stuClassName,adminName,stuDepartName,adminId);
        return Result.succ("请求成功", userInfoDto);
    }

    //学生修改
    @PutMapping("/update")
    public void updateSudent(@RequestBody Student Student){
        StudentService.update(Student);
    }

}

