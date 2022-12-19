package com.example.enter_school_management.Common.Dto;

import com.example.enter_school_management.Entity.Permission;
import com.example.enter_school_management.Entity.Student;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class PermDto {
    @NotBlank(message = "学号不能为空")
    private String stuId;
    @NotBlank(message = "学生名字不能为空")
    private String stuName;
    @NotBlank(message = "学生所在班级不能为空")
    private Long stuClassId;
    @NotBlank(message = "学生所在院系不能为空")
    private Long stuDepartId;
    List<Permission> permissions;

    public PermDto(){};

    public PermDto(Student student, List<Permission> permissions){
        this.stuId = student.getStuId();
        this.stuName = student.getStuName();
        this.stuClassId = student.getStuClassId();
        this.stuDepartId = student.getStuDepartId();
        this.setPermissions(permissions);
    }
}


