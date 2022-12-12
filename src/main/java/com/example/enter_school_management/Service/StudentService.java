package com.example.enter_school_management.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enter_school_management.Entity.Student;

import java.util.List;

public interface StudentService extends IService<Student> {
    Long getSchoolIdById(String stuId);//根据学生id获取学校id
    List<Student> getAllStudent();//获取所有学生
    List<Student> getStudentByHDStatus(int hdStatus);//根据健康日报是否填写的情况获取学生
}
