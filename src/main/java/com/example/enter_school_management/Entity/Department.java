package com.example.enter_school_management.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Department {
    @TableId(value = "dept_id", type = IdType.AUTO)
    private Long dept_id;
    @NotBlank(message = "院系名称不能为空")
    private String dept_name;
    @NotBlank(message = "院系管理员id不能为空")
    private String dept_admin_id;
}
