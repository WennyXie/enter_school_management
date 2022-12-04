package com.example.enter_school_management.Common.Dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDto {
    @NotBlank(message = "学号/工号不能为空")
    private String id;
    @NotBlank(message = "用户类型不能为空")
    private int userType;
}
