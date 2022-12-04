package com.example.enter_school_management.Entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Admin {
    @TableId(value = "admin_id")
    @NotBlank(message = "工号不能为空")
    private String adminId;
    @NotBlank(message = "管理员类型不能为空")
    private int adminType;
    @NotBlank(message = "管理员姓名不能为空")
    private String adminName;
    @NotBlank(message = "管理员电话号码不能为空")
    private String adminPhnum;
    @NotBlank(message = "管理员邮箱不能为空")
    private String adminEmail;
    @NotBlank(message = "管理员证件类型不能为空")
    private int adminIdtype;
    @NotBlank(message = "管理员证件号不能为空")
    private String adminIdnum;
}
