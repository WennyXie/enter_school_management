package com.example.enter_school_management.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Permission {
    @TableId(value = "perm_id", type = IdType.AUTO)
    private Long permId;
    @NotBlank(message = "校区id不能为空")
    private Long campusId;
    @NotBlank(message = "学号不能为空")
    private String stuId;
    @NotBlank(message = "权限不能为空")
    private int permitStatus;
}
