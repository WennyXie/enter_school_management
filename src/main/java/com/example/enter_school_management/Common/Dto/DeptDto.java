package com.example.enter_school_management.Common.Dto;

import com.example.enter_school_management.Entity.Department;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DeptDto {
    @NotBlank(message = "院系id不能为空")
    private Long deptId;
    @NotBlank(message = "院系名不能为空")
    private String deptName;
    @NotBlank(message = "院系管理员名字不能为空")
    private String adminName;
    @NotBlank(message = "院系管理员ID不能为空")
    private String adminId;

    private Integer classAmount;
    private Integer stuAmount;

    public DeptDto(Department department,String adminName, Integer classAmount, Integer stuAmount){
        this.deptId = department.getDeptId();
        this.deptName = department.getDeptName();
        this.adminId = department.getDeptAdminId();
        this.adminName = adminName;
        this.classAmount = classAmount;
        this.stuAmount = stuAmount;
    }

}
