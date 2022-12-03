package com.example.enter_school_management.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Data
public class Comin_approve {
    @TableId(value = "comin_app_id", type = IdType.AUTO)
    private Long comin_app_id;
    @NotBlank(message = "学号不能为空")
    private String stu_id;
    @NotBlank(message = "返校原因不能为空")
    private String comin_reason;
    @NotBlank(message = "预计返校日期不能为空")
    private Date exp_comindate;
    @NotBlank(message = "申请状态不能为空")
    private int status;
    private String current_admin_id;
    private String reject_reason;
    @NotBlank(message = "申请日期不能为空")
    private Date current_date;

}
