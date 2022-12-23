package com.example.enter_school_management.Common.Dto;

import com.example.enter_school_management.Entity.Department;
import com.example.enter_school_management.Entity.Campus;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class DeptandCampusDto {
    private Long deptId;
    private String deptName;

    private List<Campus> campusList;


    public DeptandCampusDto(Department department,List<Campus> campuslist){
        this.deptId = department.getDeptId();
        this.deptName = department.getDeptName();
        this.campusList = campuslist;
    }

}
