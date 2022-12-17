package com.example.enter_school_management.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enter_school_management.Entity.Student;
import com.example.enter_school_management.Mapper.StudentMapper;
import com.example.enter_school_management.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.enter_school_management.Common.lang.Const.HDNotUpdated;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Autowired
    StudentMapper studentMapper;

   // @Override
    public Student getStudentById(String stuId){
        return getOne(new QueryWrapper<Student>().eq("stu_id", stuId));
    }

    @Override
    public List<Student> getAllStudent(){
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.isNotNull("stu_id");
        return studentMapper.selectList(studentQueryWrapper);
    }

    @Override
    public Long getSchoolIdById(String stuId){
        Student student = getById(stuId);
        return student.getStuSchoolId();
    }

    @Override
    public List<Student> getStudentByHDStatus(int hdStatus){
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("hd_updated", hdStatus);
        return studentMapper.selectList(studentQueryWrapper);
    }
}
