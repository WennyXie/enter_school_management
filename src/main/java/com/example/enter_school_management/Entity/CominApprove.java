package com.example.enter_school_management.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Data
public class CominApprove {
    @TableId(value = "comin_app_id", type = IdType.AUTO)
    private Long cominAppId;
    @NotBlank(message = "学号不能为空")
    private String stuId;
    @NotBlank(message = "负责辅导员不能为空")
    private String adminId;
    @NotBlank(message = "返校原因不能为空")
    private String cominReason;
    @NotBlank(message = "预计返校日期不能为空")
    private Date expComindate;
    @NotBlank(message = "申请状态不能为空")
    private int status;
    private String currentAdminId;
    private String rejectReason;
    @NotBlank(message = "申请日期不能为空")
    private Date currentDate;

}
