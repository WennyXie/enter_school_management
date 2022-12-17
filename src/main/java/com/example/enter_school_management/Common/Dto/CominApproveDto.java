package com.example.enter_school_management.Common.Dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Data
public class CominApproveDto {
    @NotBlank(message = "学号不能为空")
    private String stuId;
    @NotBlank(message = "负责辅导员不能为空")
    private String adminId;
    @NotBlank(message = "返校原因不能为空")
    private String cominReason;
    @NotBlank(message = "预计返校日期不能为空")
    private Date expComindate;
}
