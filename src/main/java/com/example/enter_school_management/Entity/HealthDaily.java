package com.example.enter_school_management.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.sql.Time;

@Data
public class HealthDaily {
    @TableId(value = "hd_id", type = IdType.AUTO)
    private Long hdId;
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
    @NotBlank(message = "填报日期不能为空")
    private Date date;
    @NotBlank(message = "填报时间不能为空")
    private Time time;
    private Integer hdStatus;
}
