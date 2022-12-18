package com.example.enter_school_management.Common.Dto;

import com.example.enter_school_management.Entity.Student;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserInfoDto {
    @NotBlank(message = "学号不能为空")
    private String stuId;
    @NotBlank(message = "学生名字不能为空")
    private String stuName;
    @NotBlank(message = "学生电话号码不能为空")
    private String stuPhnum;
    @NotBlank(message = "学邮不能为空")
    private String stuEmail;
    @NotBlank(message = "学生证件类型不能为空")
    private int stuIdtype;
    @NotBlank(message = "学生证件号不能为空")
    private String stuIdnum;
    @NotBlank(message = "学生所在班级不能为空")
    private Long stuClassId;
    private String stuClassName;
    private String adminName;
    private String adminId;
    @NotBlank(message = "学生所在院系不能为空")
    private Long stuDepartId;
    @NotBlank(message = "学生所在学校不能为空")
    private Long stuSchoolId;
    @NotBlank(message = "健康日报填写状态不能为空")
    private int hdUpdated;
    private String  stuDepartName;

    public UserInfoDto(Student student, String stuClassName, String adminName,String  stuDepartName, String adminId){
        this.adminName = adminName;
        this.stuClassId = student.getStuClassId();
        this.stuClassName = stuClassName;
        this.stuDepartName = stuDepartName;
        this.stuDepartId = student.getStuDepartId();
        this.stuEmail = student.getStuEmail();
        this.stuId = student.getStuId();
        this.stuName = student.getStuName();
        this.stuIdnum = student.getStuIdnum();
        this.stuPhnum = student.getStuPhnum();
        this.stuIdtype = student.getStuIdtype();
        this.stuSchoolId = student.getStuSchoolId();
        this.hdUpdated = student.getHdUpdated();
        this.adminId = adminId;
    }

}
