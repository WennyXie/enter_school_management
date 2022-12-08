package com.example.enter_school_management.Controller;

import com.example.enter_school_management.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/perm")
public class PermissionController {
    @Autowired
    PermissionService permissionService;

    @Scheduled(cron = "0 0 5 * * ?")
    void updatePerm(){
        permissionService.dailyUpdatePermission();
    }
}
