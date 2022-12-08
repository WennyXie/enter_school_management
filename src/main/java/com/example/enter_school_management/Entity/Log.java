package com.example.enter_school_management.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Data
public class Log {
    @TableId(value = "log_id", type = IdType.AUTO)
    private Long logId;
    @NotBlank(message = "学号不能为空")
    private String stuId;
    @NotBlank(message = "进出校日期不能为空")
    private Date logDate;
    @NotBlank(message = "进出校时间不能为空")
    private Time logTime;
    @NotBlank(message = "进出校状态不能为空")
    private int logStatus;
    @NotBlank(message = "校区id不能为空")
    private Long campusId;
    @NotBlank(message = "院系id不能为空")
    private Long deptId;
}

