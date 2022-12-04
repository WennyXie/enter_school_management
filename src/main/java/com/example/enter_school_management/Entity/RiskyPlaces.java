package com.example.enter_school_management.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class RiskyPlaces {
    @TableId(value = "rp_id", type = IdType.AUTO)
    private Long rpId;
    @NotBlank(message = "城市不能为空")
    private String city;
    @NotBlank(message = "区不能为空")
    private String district;
    @NotBlank(message = "街道不能为空")
    private String street;
}
