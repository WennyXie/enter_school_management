package com.example.enter_school_management.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StuClass {
    @TableId(value = "class_id", type = IdType.AUTO)
    private Long classId;
    @NotBlank(message = "班级名称不能为空")
    private String className;
    @NotBlank(message = "院系id不能为空")
    private Long deptId;
    @NotBlank(message = "班级管理员id不能为空")
    private Long instructorId;
}
