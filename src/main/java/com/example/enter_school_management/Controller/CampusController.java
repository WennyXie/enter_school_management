package com.example.enter_school_management.Controller;

import com.example.enter_school_management.Common.lang.Result;
import com.example.enter_school_management.Entity.Campus;
import com.example.enter_school_management.Entity.Permission;
import com.example.enter_school_management.Service.CampusService;
import com.example.enter_school_management.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.enter_school_management.Common.lang.Const.*;

@RestController
@RequestMapping("/Campus")
public class CampusController {
    @Autowired
    CampusService campusService;
    @Autowired
    PermissionService permissionService;

    //更改校区管控状态
    @PostMapping("/changeStatus")
    public Result campusChangeStatus(Campus campus){
        campusService.updateById(campus);
        List<Permission> permissionList = permissionService.getPermissionByCampusId(campus.getCampusId());
        for(Permission p : permissionList){
            if(campus.getCampusStatus() == CampusClosed)
                p.setPermitStatus(PermDenied);
            else p.setPermitStatus(PermApproved);
        }
        return Result.succ("校区管控状态更改成功！");
    }
}
