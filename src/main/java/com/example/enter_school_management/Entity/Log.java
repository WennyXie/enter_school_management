package com.example.enter_school_management.Entity;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Log {
    @TableId(value = "log_id", type = IdType.AUTO)
    private Long log_id;
    @NotBlank(message = "学号不能为空")
    private String stu_id;
    @NotBlank(message = "进出校时间不能为空")
    private DateTime log_datetime;
    @NotBlank(message = "进出校状态不能为空")
    private int log_status;
    @NotBlank(message = "校区id不能为空")
    private long campus_id;
    @NotBlank(message = "院系id不能为空")
    private long dept_id;
}

