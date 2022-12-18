package com.example.enter_school_management.Controller;

import com.example.enter_school_management.Common.Dto.GetLACADto;
import com.example.enter_school_management.Common.Dto.LeaveApplicationDto;
import com.example.enter_school_management.Common.lang.Const;
import com.example.enter_school_management.Common.lang.Result;
import com.example.enter_school_management.Entity.Admin;
import com.example.enter_school_management.Entity.LeaveApplication;
import com.example.enter_school_management.Entity.RiskyPlaces;
import com.example.enter_school_management.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

import java.util.List;
import java.util.Objects;

import static com.example.enter_school_management.Common.lang.Const.*;
import static java.sql.Types.NULL;

@RestController
@RequestMapping("/LA")
public class LeaveApplicationController {
    @Autowired
    LeaveApplicationService leaveApplicationService;
    @Autowired
    AdminService adminService;
    @Autowired
    RiskyPlacesService riskyPlacesService;
    @Autowired
    StuClassService stuClassService;
    @Autowired
    DepartmentService departmentService;

    //学生填写离校申请
    @PostMapping("/fillin")
    public Result fillInLA(@RequestBody LeaveApplicationDto leaveApplicationDto){
        leaveApplicationService.saveLA(leaveApplicationDto);
        return Result.succ("出校申请提交成功！等待审核中——");
    }

    //管理员获取所有职权范围内的离校申请
    @PostMapping("/getAllDuty")
    public Result getAllDutyLA(@RequestParam String adminId){
        List<LeaveApplication> leaveApplications = leaveApplicationService.getLAByDuty(adminId);
        if (leaveApplications.size() == 0) return Result.fail("目前没有未处理的申请或未找到对应管理员！");
        else return Result.succ("离校申请审核列表获取成功！",leaveApplications);
    }

    //检查学生要去的地区是否为风险地区
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

    //拒绝学生的离校申请
    @PostMapping("/reject")
    public Result rejectLA(@RequestParam Long LAId,@RequestParam String rejectReason,@RequestParam String adminID){
        LeaveApplication LA = leaveApplicationService.getById(LAId);
        LA.setRejectReason(rejectReason);
        LA.setStatus(appReject);
        LA.setCurrentAdminId(adminID);
        leaveApplicationService.updateById(LA);
        return Result.succ("拒绝离校申请成功！");
    }

    //同意学生的离校申请
    @PostMapping("/approve")
    public Result approveLA(@RequestParam Long LAId,@RequestParam String adminID){
        LeaveApplication LA = leaveApplicationService.getById(LAId);
        Admin currentAdmin = adminService.getById(adminID);
        LA.setCurrentAdminId(adminID);
        if(currentAdmin.getAdminType() == roleInstructor) {
            LA.setStatus(appITApprove);
            LA.setAdminId(departmentService.getById(stuClassService.getClassByAdmin(adminID).getDeptId()).getDeptAdminId());
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

    //获取学生的所有离校申请
    @GetMapping("/myLA")
    public Result getMyLA(@RequestParam String stuId){
        List<LeaveApplication> leaveApplications = leaveApplicationService.getLAByStuId(stuId);
        return Result.succ("获取出校申请列表成功！",leaveApplications);
    }

    //查询学生的离校申请，支持按状态进行筛选
    @PostMapping("/stuLA")
    public Result getstuLA(@RequestBody GetLACADto getLACADto){
        if(getLACADto.getStatus() == appSubmit){
            return Result.succ(leaveApplicationService.getLAByStuIdAndStatus(getLACADto.getStuId(),appSubmit));
        }
        else if(getLACADto.getStatus() == appITApprove){
            return Result.succ(leaveApplicationService.getLAByStuIdAndStatus(getLACADto.getStuId(),appITApprove));
        }
        else if(getLACADto.getStatus() == appDAApprove){
            return Result.succ(leaveApplicationService.getLAByStuIdAndStatus(getLACADto.getStuId(),appDAApprove));
        }
        else if(getLACADto.getStatus() == appReject){
            return Result.succ(leaveApplicationService.getLAByStuIdAndStatus(getLACADto.getStuId(),appReject));
        }
        else{
            return Result.succ(leaveApplicationService.getLAByStuId(getLACADto.getStuId()));
        }
    }

    //过去n天尚未批准的离校申请数量及详细信息
    @PostMapping("/lastndayUncheck")
    public Result lastndayUncheck(@RequestParam int days){
        List<LeaveApplication> leaveApplications = leaveApplicationService.getLastNDayUncheckedLA(days);
        return Result.succ(leaveApplications.size(),leaveApplications);
    }

}
