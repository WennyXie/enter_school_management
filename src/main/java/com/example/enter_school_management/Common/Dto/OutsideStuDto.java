package com.example.enter_school_management.Common.Dto;

import com.example.enter_school_management.Entity.Log;
import com.example.enter_school_management.Entity.Student;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class OutsideStuDto {
    @NotBlank(message = "学生信息不能为空")
    Student student;
    @NotBlank(message = "离校记录不能为空")
    Log log;
}
