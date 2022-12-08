package com.example.enter_school_management.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enter_school_management.Common.Dto.LogDto;
import com.example.enter_school_management.Entity.Log;

import java.sql.Date;
import java.util.List;

public interface LogService extends IService<Log> {
    Long saveLog(LogDto logDto);
    Log getLatestLog(String stuId);
    List<Log> getYesterdayLogByStuId(Date yesterday, String stuId);
}
