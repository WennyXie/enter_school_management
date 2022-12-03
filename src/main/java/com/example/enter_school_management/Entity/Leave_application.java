package com.example.enter_school_management.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Data
public class Leave_application {
    @TableId(value = "leav_app_id", type = IdType.AUTO)
    private Long leav_app_id;
    @NotBlank(message = "学号不能为空")
    private String stu_id;
    @NotBlank(message = "离校原因不能为空")
    private String leav_reason;
    @NotBlank(message = "目的地城市不能为空")
    private String dest_city;
    @NotBlank(message = "目的地区不能为空")
    private String dest_district;
    @NotBlank(message = "目的地街道不能为空")
    private String dest_street;
    @NotBlank(message = "预计离校日期不能为空")
    private Date exp_leavdate;
    @NotBlank(message = "预计返校日期不能为空")
    private Date exp_retdate;
    @NotBlank(message = "申请状态不能为空")
    private int status;
    private String current_admin_id;
    private String reject_reason;
    @NotBlank(message = "申请日期不能为空")
    private Date current_date;
}
