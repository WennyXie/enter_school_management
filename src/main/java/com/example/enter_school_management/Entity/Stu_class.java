package com.example.enter_school_management.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Stu_class {
    @TableId(value = "class_id", type = IdType.AUTO)
    private Long class_id;
    @NotBlank(message = "班级名称不能为空")
    private String class_name;
    @NotBlank(message = "院系id不能为空")
    private String dept_id;
    @NotBlank(message = "班级管理员id不能为空")
    private String instructor_id;
}
