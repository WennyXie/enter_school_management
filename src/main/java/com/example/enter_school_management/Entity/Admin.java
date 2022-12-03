package com.example.enter_school_management.Entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Admin {
    @TableId(value = "admin_id")
    @NotBlank(message = "工号不能为空")
    private String admin_id;
    @NotBlank(message = "管理员类型不能为空")
    private int admin_type;
    @NotBlank(message = "管理员姓名不能为空")
    private String admin_name;
    @NotBlank(message = "管理员电话号码不能为空")
    private String admin_phnum;
    @NotBlank(message = "管理员邮箱不能为空")
    private String admin_email;
    @NotBlank(message = "管理员证件类型不能为空")
    private int admin_idtype;
    @NotBlank(message = "管理员证件号不能为空")
    private String admin_idnum;
}
