package com.example.enter_school_management.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.enter_school_management.Entity.LeaveApplication;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LeaveApplicationMapper extends BaseMapper<LeaveApplication> {
}
