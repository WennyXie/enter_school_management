package com.example.enter_school_management.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Permission {
    @TableId(value = "campus_id")
    @NotBlank(message = "校区id不能为空")
    private long campus_id;
    @TableId(value = "stu_id")
    @NotBlank(message = "学号不能为空")
    private String stu_id;
    @NotBlank(message = "权限不能为空")
    private int permit_status;
}
