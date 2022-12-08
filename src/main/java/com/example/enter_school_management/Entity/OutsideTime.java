package com.example.enter_school_management.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.sql.Time;

@Data
public class OutsideTime {
    @TableId(value = "ot_id", type = IdType.AUTO)
    @NotBlank(message = "出校时间id不能为空")
    private Long otId;
    @NotBlank(message = "学号不能为空")
    private String stuId;
    @NotBlank(message = "日期不能为空")
    private Date date;
    @NotBlank(message = "时长不能为空")
    private Long otTime;
}
