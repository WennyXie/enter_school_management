package com.example.enter_school_management.Entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Student {
    private static final long serialVersionUID = 1L;
    @TableId(value = "stu_id")
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
    @NotBlank(message = "学生所在学校不能为空")
    private Long stuSchoolId;
    @NotBlank(message = "健康日报填写状态不能为空")
    private int hdUpdated;
}
