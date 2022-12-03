package com.example.enter_school_management.Entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.sql.Time;

@Data
public class Outside_time {
    @TableId(value = "ot_id")
    @NotBlank(message = "出校时间id不能为空")
    private long ot_id;
    @NotBlank(message = "学号不能为空")
    private String stu_id;
    @NotBlank(message = "日期不能为空")
    private Date date;
    @NotBlank(message = "时长不能为空")
    private Time time;
}
