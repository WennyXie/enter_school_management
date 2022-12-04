package com.example.enter_school_management.Common.Dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LogDto {
    @NotBlank(message = "学号不能为空")
    private String stuId;
    @NotBlank(message = "校区id不能为空")
    private Long campusId;
}
