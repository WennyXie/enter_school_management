package com.example.enter_school_management.Controller;

import com.example.enter_school_management.Common.Dto.LoginDto;
import com.example.enter_school_management.Common.lang.Result;
import com.example.enter_school_management.Entity.Admin;
import com.example.enter_school_management.Entity.Student;
import com.example.enter_school_management.Service.AdminService;
import com.example.enter_school_management.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.enter_school_management.Common.lang.Const.*;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    StudentService studentService;

    @Autowired
    AdminService adminService;

    @PostMapping("/test")
    public Result getAll(@RequestParam String stuId){
        List<Student> studentList = studentService.getAllStudent(stuId);
        return Result.succ(studentList);
    }

    //userType为0代表学生，为1代表辅导员，为2代表院系管理员，为3代表超级管理员
    @PostMapping("/")
    public Result userLogin(@RequestBody LoginDto loginDto){
        int userType = loginDto.getUserType();
        String id = loginDto.getId();
        if(userType == roleStudent){
            if(studentService.getStudentById(id) != null)
                return Result.succ("学生登陆成功！");
            return Result.fail("未查询到该学生信息！");
        }
        else {
            Admin admin = adminService.getAdminById(id);
            if (admin != null && admin.getAdminType() == userType) {
                if (userType == roleInstructor) return Result.succ("辅导员登陆成功！");
                if (userType == roleDeptAdmin) return Result.succ("院系管理员登陆成功！");
                if (userType == roleSuperAdmin) return Result.succ("超级管理员登陆成功！");
            }
            return Result.fail("未查询到该管理员信息！");
        }
    }
}
