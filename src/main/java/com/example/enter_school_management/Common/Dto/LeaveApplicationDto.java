package com.example.enter_school_management.Common.Dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Data
public class LeaveApplicationDto {
    @NotBlank(message = "学号不能为空")
    private String stuId;
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
}
