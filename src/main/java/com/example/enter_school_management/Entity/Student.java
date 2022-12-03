package com.example.enter_school_management.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Student {
    @TableId(value = "stu_id")
    @NotBlank(message = "学号不能为空")
    private String stu_id;
    @NotBlank(message = "学生名字不能为空")
    private String stu_name;
    @NotBlank(message = "学生电话号码不能为空")
    private String stu_phnum;
    @NotBlank(message = "学邮不能为空")
    private String stu_email;
    @NotBlank(message = "学生证件类型不能为空")
    private int stu_idtype;
    @NotBlank(message = "学生证件号不能为空")
    private String stu_idnum;
    @NotBlank(message = "学生所在班级不能为空")
    private long stu_class_id;
}
