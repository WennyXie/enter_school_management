package com.example.enter_school_management.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Data
public class LeaveApplication {
    @TableId(value = "leav_app_id", type = IdType.AUTO)
    private Long leavAppId;
    @NotBlank(message = "学号不能为空")
    private String stuId;
    @NotBlank(message = "负责辅导员不能为空")
    private String adminId;
    @NotBlank(message = "离校原因不能为空")
    private String leavReason;
    @NotBlank(message = "目的地城市不能为空")
    private String destCity;
    @NotBlank(message = "目的地区不能为空")
    private String destDistrict;
    @NotBlank(message = "目的地街道不能为空")
    private String destStreet;
    @NotBlank(message = "预计离校日期不能为空")
    private Date expLeavdate;
    @NotBlank(message = "预计返校日期不能为空")
    private Date expRetdate;
    @NotBlank(message = "申请状态不能为空")
    private int status;
    private String currentAdminId;
    private String rejectReason;
    @NotBlank(message = "申请日期不能为空")
    private Date currentDate;
}
