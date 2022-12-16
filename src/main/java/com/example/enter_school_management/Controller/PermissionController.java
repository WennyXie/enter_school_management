package com.example.enter_school_management.Controller;

import com.example.enter_school_management.Common.lang.Result;
import com.example.enter_school_management.Entity.Permission;
import com.example.enter_school_management.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perm")
public class PermissionController {
    @Autowired
    PermissionService permissionService;

    //每日五点更新每位同学的进校权限
    @Scheduled(cron = "0 0 5 * * ?")
    void updatePerm(){
        permissionService.dailyUpdatePermission();
    }

    //查询学生的入校权限
    @PostMapping("/getperm")
    public Result getPerm(@RequestParam String stuId){
        List<Permission> permissions = permissionService.getPermissionByStuid(stuId);
        return Result.succ(permissions);
    }
}
