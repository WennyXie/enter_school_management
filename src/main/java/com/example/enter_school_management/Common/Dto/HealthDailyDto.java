package com.example.enter_school_management.Common.Dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class HealthDailyDto {
    @NotBlank(message = "学生id不能为空")
    private String stuId;
    @NotBlank(message = "体温不能为空")
    private Float bodyTemperature;
    @NotBlank(message = "所在城市不能为空")
    private String city;
    @NotBlank(message = "所在区不能为空")
    private String district;
    @NotBlank(message = "所在街道不能为空")
    private String street;
}
