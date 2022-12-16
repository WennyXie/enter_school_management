package com.example.enter_school_management.Common.Dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GetLACADto {
    @NotBlank(message = "学生id不能为空")
    private String stuId;
    private int status;
}
