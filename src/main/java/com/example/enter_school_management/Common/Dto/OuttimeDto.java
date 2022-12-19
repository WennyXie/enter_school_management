package com.example.enter_school_management.Common.Dto;

import com.example.enter_school_management.Entity.Student;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class OuttimeDto {
    @NotBlank(message = "学生信息不能为空")
    Student student;
    @NotBlank(message = "总离校时长不能为空")
    Long totalOT;

    public OuttimeDto(){};

    public OuttimeDto(Student student, Long totalOT){
        this.setStudent(student);
        this.setTotalOT(totalOT);
    }

}
