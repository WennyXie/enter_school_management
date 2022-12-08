package com.example.enter_school_management.Controller;

import com.example.enter_school_management.Common.Dto.LeaveApplicationDto;
import com.example.enter_school_management.Common.lang.Result;
import com.example.enter_school_management.Entity.Admin;
import com.example.enter_school_management.Entity.LeaveApplication;
import com.example.enter_school_management.Entity.RiskyPlaces;
import com.example.enter_school_management.Service.AdminService;
import com.example.enter_school_management.Service.LeaveApplicationService;
import com.example.enter_school_management.Service.RiskyPlacesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

import java.util.List;
import java.util.Objects;

import static com.example.enter_school_management.Common.lang.Const.*;

@RestController
@RequestMapping("/LA")
public class LeaveApplicationController {
    @Autowired
    LeaveApplicationService leaveApplicationService;
    @Autowired
    AdminService adminService;
    @Autowired
    RiskyPlacesService riskyPlacesService;

    @PostMapping("/fillin")
    public Result fillInLA(@RequestBody LeaveApplicationDto leaveApplicationDto){
        leaveApplicationService.saveLA(leaveApplicationDto);
        return Result.succ("出校申请提交成功！等待审核中——");
    }

    @PostMapping("/getAllDuty")
    public Result getAllDutyLA(@RequestParam String adminId){
        Admin currentAdmin = adminService.getAdminById(adminId);
        if(currentAdmin.getAdminType() == roleInstructor) {
            List<LeaveApplication> leaveApplications = leaveApplicationService.getLAByStatus(appSubmit);
            return Result.succ("辅导员离校申请审核列表获取成功！",leaveApplications);
        }
        else if(currentAdmin.getAdminType() == roleDeptAdmin){
            List<LeaveApplication> leaveApplications = leaveApplicationService.getLAByStatus(appITApprove);
            return Result.succ("院系管理员离校申请审核列表获取成功！",leaveApplications);
        }
        else if(currentAdmin.getAdminType() == roleSuperAdmin){
            List<LeaveApplication> leaveApplications = leaveApplicationService.getAllLA();
            return Result.succ("超级管理员离校申请列表获取成功！",leaveApplications);
        }
        return Result.fail("未找到对应管理员！");
    }

    @PostMapping("/check")
    public Result checkLA(@RequestParam Long LAId){
        LeaveApplication LA = leaveApplicationService.getById(LAId);
        List<RiskyPlaces> riskyPlaces = riskyPlacesService.getAllRP();
        for (RiskyPlaces r:riskyPlaces){
            if(Objects.equals(LA.getDestCity(), r.getCity()) && Objects.equals(LA.getDestDistrict(), r.getDistrict())
                && Objects.equals(LA.getDestStreet(), r.getStreet())){
                return Result.fail("该生将前往风险地区。");
            }
        }
        return Result.succ("该生前往地区目前无风险");
    }

    @PostMapping("/reject")
    public Result rejectLA(@RequestParam Long LAId,@RequestParam String rejectReason,@RequestParam String adminID){
        LeaveApplication LA = leaveApplicationService.getById(LAId);
        LA.setRejectReason(rejectReason);
        LA.setStatus(appReject);
        LA.setCurrentAdminId(adminID);
        leaveApplicationService.updateById(LA);
        return Result.succ("拒绝离校申请成功！");
    }

    @PostMapping("/approve")
    public Result approveLA(@RequestParam Long LAId,@RequestParam String adminID){
        LeaveApplication LA = leaveApplicationService.getById(LAId);
        Admin currentAdmin = adminService.getAdminById(adminID);
        LA.setCurrentAdminId(adminID);
        if(currentAdmin.getAdminType() == roleInstructor) {
            LA.setStatus(appITApprove);
            leaveApplicationService.updateById(LA);
            return Result.succ("辅导员审批通过成功！");
        }
        else if(currentAdmin.getAdminType() == roleDeptAdmin){
            LA.setStatus(appDAApprove);
            leaveApplicationService.updateById(LA);
            return Result.succ("院系管理员审批通过成功！");
        }
        return Result.fail("未找到对应管理员！");
    }

    @PostMapping("/myLA")
    public Result getMyLA(@RequestParam String stuId){
        List<LeaveApplication> leaveApplications = leaveApplicationService.getLAByStuId(stuId);
        return Result.succ("获取出校申请列表成功！",leaveApplications);
    }

}
