package com.example.enter_school_management.Controller;

import com.example.enter_school_management.Common.Dto.LogDto;
import com.example.enter_school_management.Common.lang.Result;
import com.example.enter_school_management.Entity.Log;
import com.example.enter_school_management.Service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class LogController {
    @Autowired
    LogService logService;

    @PostMapping("/slashcard")
    public Result slashCard(@RequestBody LogDto logDto){
        Long logId = logService.saveLog(logDto);
        if(logId == -1) return  Result.fail("出校校区与该生已在校区不符！");
        Log log = logService.getById(logId);
        if(log.getLogStatus() == 0){
            return Result.succ("出校成功！");
        }
        return Result.succ("进校成功！");
    }
}
