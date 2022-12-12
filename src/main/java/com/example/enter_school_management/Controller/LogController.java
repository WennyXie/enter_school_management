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

import static com.example.enter_school_management.Common.lang.Const.outSchool;

@RestController
@RequestMapping("/log")
public class LogController {
    @Autowired
    LogService logService;

    //学生刷卡进出校并进行记录
    @PostMapping("/slashcard")
    public Result slashCard(@RequestBody LogDto logDto){
        Long logId = logService.saveLog(logDto);
        if(logId == -1) return  Result.fail("出校校区与该生已在校区不符！");
        Log log = logService.getById(logId);
        if(log.getLogStatus() == outSchool){
            return Result.succ("出校成功！");
        }
        return Result.succ("进校成功！");
    }
}
