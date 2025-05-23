package com.example.enter_school_management.Controller;

import com.example.enter_school_management.Common.lang.Result;
import com.example.enter_school_management.Entity.Campus;
import com.example.enter_school_management.Entity.Permission;
import com.example.enter_school_management.Service.CampusService;
import com.example.enter_school_management.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/changeStatus")
    public Result campusChangeStatus(@RequestBody Campus campus){
        campusService.updateById(campus);
        List<Permission> permissionList = permissionService.getPermissionByCampusId(campus.getCampusId());
        for(Permission p : permissionList){
            if(campus.getCampusStatus() == CampusClosed)
                p.setPermitStatus(PermDenied);
            else p.setPermitStatus(PermApproved);
            permissionService.updateById(p);
        }
        return Result.succ("校区管控状态更改成功！");
    }

    @GetMapping("/getallCampus")
    public Result getallCampus(){
        List<Campus> campusList = campusService.getAllCampus();
        return Result.succ("获取数据成功",campusList);
    }
}
