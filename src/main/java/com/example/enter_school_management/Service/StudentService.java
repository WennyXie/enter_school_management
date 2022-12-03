package com.example.enter_school_management.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enter_school_management.Entity.Student;

import java.util.List;

public interface StudentService extends IService<Student> {
    Student getStudentById(String stu_id);
    List<Student> getAllStudent();
}
