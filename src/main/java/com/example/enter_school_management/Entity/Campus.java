package com.example.enter_school_management.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Campus {
    @TableId(value = "campus_id", type = IdType.AUTO)
    private Long campus_id;
    @NotBlank(message = "校区名称不能为空")
    private String campus_name;
}
