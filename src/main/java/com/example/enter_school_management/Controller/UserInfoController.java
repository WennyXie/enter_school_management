package com.example.enter_school_management.Controller;

import com.example.enter_school_management.Common.lang.Result;
import com.example.enter_school_management.Common.Dto.UserInfoDto;
import com.example.enter_school_management.Common.Dto.DeptDto;
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

import java.util.ArrayList;
import java.util.List;


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

    //辅导员对应所有学生
    @GetMapping("/allStudent")
    public Result getallStudenList(@RequestParam String adminId){
        StuClass aClass = StuClassService.getClassByAdmin(adminId);
        List<Student> studntList = StudentService.getByClass(aClass.getClassId());
        return Result.succ("获取学生数据成功", studntList);
    }

    //院系管理员对应的所有班级
    @GetMapping("/allClass")
    public Result getallClassList(@RequestParam String adminId){
        Department department = DepartmentService.getDepartByAdmin(adminId);
        List<StuClass> classes =  StuClassService.getClassByDept(department.getDeptId());
        List<List<Student>> students = new ArrayList<>();
        List<Integer> totalStudentNum = new ArrayList<>();
        List<Admin> adminList  =new ArrayList<>();
        for(StuClass stuClass: classes){
            Admin admin=AdminService.getById(stuClass.getInstructorId());
            List<Student> studntList = StudentService.getByClass(stuClass.getClassId());
            totalStudentNum.add(studntList.size());
            students.add(studntList);
            adminList.add(admin);
        }
        return Result.succ("获取班级数据成功",classes,adminList,totalStudentNum,students);
    }

    @GetMapping("/allDepartment")
    public Result getalldeptment(){
        List<DeptDto> AllDept = new ArrayList<>();
        List<Department> departmentList = DepartmentService.getAllDepart();
        for(Department department:departmentList){
            Admin admin = AdminService.getById(department.getDeptAdminId());
            List<StuClass> stuClassList = StuClassService.getClassByDept(department.getDeptId());
            Integer classAmount = stuClassList.size();
            List<Student> studentList = StudentService.getByDepart(department.getDeptId());
            Integer stuAmount = studentList.size();
            DeptDto deptDto = new DeptDto(department,admin.getAdminName(),classAmount,stuAmount);
            AllDept.add(deptDto);
        }
        return Result.succ("获取数据成功",AllDept);
    }

    @GetMapping("/byDepartment")
    public Result getstudentbydeptment(@RequestParam Long deptId){
        List<UserInfoDto> userInfoDtoList = new ArrayList<>();
        List<Student> studentList = StudentService.getByDepart(deptId);
        for(Student student:studentList){
            StuClass stuClass = StuClassService.getById(student.getStuClassId());
            String stuClassName = stuClass.getClassName();
            Admin admin = AdminService.getById(stuClass.getInstructorId());
            String adminName = admin.getAdminName();
            String adminId = admin.getAdminId();
            Department department = DepartmentService.getById(student.getStuDepartId());
            String stuDepartName = department.getDeptName();
            UserInfoDto userInfoDto = new UserInfoDto(student,stuClassName,adminName,stuDepartName,adminId);
            userInfoDtoList.add(userInfoDto);
        }
        return Result.succ("获取数据成功",userInfoDtoList);
    }

}

