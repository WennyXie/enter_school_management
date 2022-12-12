package com.example.enter_school_management.Controller;

import com.example.enter_school_management.Common.Dto.CominApproveDto;
import com.example.enter_school_management.Common.Dto.LeaveApplicationDto;
import com.example.enter_school_management.Common.lang.Result;
import com.example.enter_school_management.Entity.*;
import com.example.enter_school_management.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static com.example.enter_school_management.Common.lang.Const.*;

@RestController
@RequestMapping("/CA")
public class CominApproveController {
    @Autowired
    AdminService adminService;
    @Autowired
    RiskyPlacesService riskyPlacesService;
    @Autowired
    CominApproveService cominApproveService;
    @Autowired
    HealthDailyService healthDailyService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    CampusService campusService;

    //学生填写返校申请
    @PostMapping("/fillin")
    public Result fillInCA(@RequestBody CominApproveDto cominApproveDto){
        cominApproveService.saveCA(cominApproveDto);
        return Result.succ("返校申请提交成功！等待审核中——");
    }

    //管理员获取所有职权范围内的返校申请
    @PostMapping("/getAllDuty")
    public Result getAllDutyCA(@RequestParam String adminId){
        Admin currentAdmin = adminService.getById(adminId);
        if(currentAdmin.getAdminType() == roleInstructor) {
            List<CominApprove> cominApproves = cominApproveService.getCAByStatus(appSubmit);
            return Result.succ("辅导员返校申请审核列表获取成功！",cominApproves);
        }
        else if(currentAdmin.getAdminType() == roleDeptAdmin){
            List<CominApprove> cominApproves = cominApproveService.getCAByStatus(appITApprove);
            return Result.succ("院系管理员返校申请审核列表获取成功！",cominApproves);
        }
        else if(currentAdmin.getAdminType() == roleSuperAdmin){
            List<CominApprove> cominApproves = cominApproveService.getAllCA();
            return Result.succ("超级管理员返校申请列表获取成功！",cominApproves);
        }
        return Result.fail("未找到对应管理员！");
    }

    //检查返回的7日健康日报是否有异常
    @PostMapping("/check")
    public Result checkCA(@RequestParam Long CAId){
        CominApprove CA = cominApproveService.getById(CAId);
        List<HealthDaily> healthDailies = healthDailyService.getLastnDayHealthDaily(CA.getStuId(),7);
        for(HealthDaily HD: healthDailies){
            if(HD.getHdStatus() == HDUnsafe){
                return Result.fail("该生7日健康日报中有异常",HD);
            }
        }
        return Result.succ("该生7日健康日报正常！");
    }

    //管理员拒绝学生的返校申请
    @PostMapping("/reject")
    public Result rejectLA(@RequestParam Long CAId,@RequestParam String rejectReason,@RequestParam String adminID){
        CominApprove CA = cominApproveService.getById(CAId);
        CA.setRejectReason(rejectReason);
        CA.setStatus(appReject);
        CA.setCurrentAdminId(adminID);
        cominApproveService.updateById(CA);
        return Result.succ("拒绝返校申请成功！");
    }

    //管理员同意学生的返校申请
    @PostMapping("/approve")
    public Result approveCA(@RequestParam Long CAId,@RequestParam String adminID){
        CominApprove CA = cominApproveService.getById(CAId);
        Admin currentAdmin = adminService.getById(adminID);
        CA.setCurrentAdminId(adminID);
        if(currentAdmin.getAdminType() == roleInstructor) {
            CA.setStatus(appITApprove);
            cominApproveService.updateById(CA);
            return Result.succ("辅导员审批通过成功！");
        }
        else if(currentAdmin.getAdminType() == roleDeptAdmin){
            CA.setStatus(appDAApprove);
            cominApproveService.updateById(CA);
            List<Permission> permissions = permissionService.getPermissionByStuid(CA.getStuId());
            for(Permission p: permissions){
                if(campusService.getById(p.getCampusId()).getCampusStatus() == CampusOpen){
                    p.setPermitStatus(PermApproved);
                    permissionService.updateById(p);
                }
            }
            return Result.succ("院系管理员审批通过成功！非管控校区入校权限已经开启！");
        }
        return Result.fail("未找到对应管理员！");
    }

    //学生查询自己所有的返校申请
    @PostMapping("/myLA")
    public Result getMyLA(@RequestParam String stuId){
        List<CominApprove> cominApproves = cominApproveService.getCAByStuId(stuId);
        return Result.succ("获取返校申请列表成功！",cominApproves);
    }

}
