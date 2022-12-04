package com.example.enter_school_management.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class School {
    @TableId(value = "school_id", type = IdType.AUTO)
    private Long schoolId;
    @NotBlank(message = "学校名称不能为空")
    private String schoolName;
    @NotBlank(message = "学校所在城市不能为空")
    private String schoolCity;
}
