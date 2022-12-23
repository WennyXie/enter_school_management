package com.example.enter_school_management.Controller;

import com.example.enter_school_management.Common.Dto.CominApproveDto;
import com.example.enter_school_management.Common.Dto.GetLACADto;
import com.example.enter_school_management.Common.Dto.LeaveApplicationDto;
import com.example.enter_school_management.Common.lang.Result;
import com.example.enter_school_management.Entity.*;
import com.example.enter_school_management.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
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
    @Autowired
    StuClassService stuClassService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    StudentService studentService;

    //学生填写返校申请
    @PostMapping("/fillin")
    public Result fillInCA(@RequestBody CominApproveDto cominApproveDto){
        System.out.println(cominApproveDto);
        cominApproveService.saveCA(cominApproveDto);
        return Result.succ("返校申请提交成功！等待审核中——");
    }

    //管理员获取所有职权范围内的返校申请
    @PostMapping("/getAllDuty")
    public Result getAllDutyCA(@RequestParam String adminId){
        List<CominApprove> cominApproves = cominApproveService.getCAByDuty(adminId);
        if (cominApproves.size() == 0) return Result.fail("目前没有未处理的申请或未找到对应管理员！");
        else return Result.succ("返校申请审核列表获取成功！",cominApproves);
    }

    //检查返回的7日健康日报是否有异常
    @GetMapping("/check")
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
    @GetMapping("/reject")
    public Result rejectLA(@RequestParam Long CAId,@RequestParam String rejectReason,@RequestParam String adminID){
        CominApprove CA = cominApproveService.getById(CAId);
        CA.setRejectReason(rejectReason);
        CA.setAppStatus(appReject);
        CA.setCurrentAdminId(adminID);
        System.out.println(CA);
        cominApproveService.updateById(CA);
        return Result.succ("拒绝返校申请成功！");
    }

    //管理员同意学生的返校申请
    @GetMapping("/approve")
    public Result approveCA(@RequestParam Long CAId,@RequestParam String adminID){
        CominApprove CA = cominApproveService.getById(CAId);
        Admin currentAdmin = adminService.getById(adminID);
        CA.setCurrentAdminId(adminID);
        if(currentAdmin.getAdminType() == roleInstructor) {
            CA.setAppStatus(appITApprove);
            cominApproveService.updateById(CA);
            CA.setAdminId(departmentService.getById(stuClassService.getClassByAdmin(adminID).getDeptId()).getDeptAdminId());
            return Result.succ("辅导员审批通过成功！");
        }
        else if(currentAdmin.getAdminType() == roleDeptAdmin){
            CA.setAppStatus(appDAApprove);
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
    @GetMapping("/myCA")
    public Result getMyLA(@RequestParam String stuId){
        List<CominApprove> cominApproves = cominApproveService.getCAByStuId(stuId);
        return Result.succ("获取返校申请列表成功！",cominApproves);
    }

    //学生自己查询学生的返校申请，支持按状态进行筛选，注意status如果不传值的话过来默认为0，所以请传值4来获取所有的LA，下同
    @PostMapping("/stuCA")
    public Result getstuCA(@RequestBody GetLACADto getLACADto){
        int status = getLACADto.getStatus();
        if(status != appSubmit && status != appITApprove && status != appDAApprove && status != appReject)
            return Result.succ(cominApproveService.getCAByStuId(getLACADto.getId()));
        return Result.succ(cominApproveService.getCAByStuIdAndStatus(getLACADto.getId(),status));
    }

    //辅导员查询该班学生的返校申请，支持按状态进行筛选
    @PostMapping("/instr/stuCA")
    public Result instrGetStuCA(@RequestBody GetLACADto getLACADto){
        List<Student> studentList = studentService.getByClass(stuClassService.getClassByAdmin(getLACADto.getId()).getClassId());
        List<CominApprove> classCA = new ArrayList<>();
        int status = getLACADto.getStatus();
        if(status != appSubmit && status != appITApprove && status != appDAApprove && status != appReject) {
            for(Student s: studentList){
                List<CominApprove> cominApproves = cominApproveService.getCAByStuId(s.getStuId());
                classCA.addAll(cominApproves);
            }
            return Result.succ(classCA);
        }
        else{
            for(Student s: studentList){
                List<CominApprove> cominApproves = cominApproveService.getCAByStuIdAndStatus(s.getStuId(),status);
                classCA.addAll(cominApproves);
            }
            return Result.succ(classCA);
        }
    }

    //院系管理员查询该院学生的返校申请，支持按状态进行筛选
    @PostMapping("/DA/stuCA")
    public Result DAGetStuCA(@RequestBody GetLACADto getLACADto){
        List<Student> studentList = studentService.getByDepart(departmentService.getDepartByAdmin(getLACADto.getId()).getDeptId());
        List<CominApprove> deptCA = new ArrayList<>();
        int status = getLACADto.getStatus();
        if(status != appSubmit && status != appITApprove && status != appDAApprove && status != appReject) {
            for(Student s: studentList){
                List<CominApprove> cominApproves = cominApproveService.getCAByStuId(s.getStuId());
                deptCA.addAll(cominApproves);
            }
            return Result.succ(deptCA);
        }
        else{
            for(Student s: studentList){
                List<CominApprove> cominApproves = cominApproveService.getCAByStuIdAndStatus(s.getStuId(),status);
                deptCA.addAll(cominApproves);
            }
            return Result.succ(deptCA);
        }
    }

    //过去n天尚未批准的入校申请数量及详细信息
    @GetMapping("/lastndayUncheck")
    public Result lastndayUncheck(@RequestParam int days){
        List<CominApprove> cominApproves = cominApproveService.getLastNDayUncheckedLA(days);
        return Result.succ(cominApproves.size(),cominApproves);
    }

    //班级前n个提交入校申请最多的学生
    @GetMapping("/class/nmostSubmit")
    public Result getclassMostSubmit(@RequestParam String adminId , @RequestParam Integer n) {
        StuClass aClass = stuClassService.getClassByAdmin(adminId); //获取辅导员管理的班级
        List<Student> studntList = studentService.getByClass(aClass.getClassId());//获取班级的所有学生
        int stuNum =studntList.size();
        if(stuNum < n){
            n =stuNum;
        }
        List<Student> Substudents = new ArrayList<>();
        List<Integer> num = new ArrayList<>();
        List<Integer> Subnum = new ArrayList<>();
        List<Integer> indexs = new ArrayList<>();
        for(Student student:studntList){
            String id = student.getStuId();
            List<CominApprove> cominApproveList = cominApproveService.getCAByStuId(id);
            num.add(cominApproveList.size());
        }
        System.out.println(studntList);
        System.out.println(num);
        int old = 0;
        for(int i=0; i < n;i++){
            Integer max = Collections.max(num);
            if(max == 0){
                break;
            }
            int index = num.indexOf(max);
            if( index == 0 && i == 0 ){
                indexs.add(index);
                Subnum.add(max);
                num.remove(index);
                old = index;
            }else {
                if(old > index){
                    index = index + i - 1;
                    indexs.add(index);
                    Subnum.add(max);
                    num.remove(index-i+1);
                    old = index-i+1;
                    continue;
                }
                index = index + i;
                indexs.add(index);
                Subnum.add(max);
                num.remove(index-i);
                old = index-i;
            }
        }
        System.out.println(indexs);
        for(Integer theIndex:indexs){
            Substudents.add(studntList.get(theIndex));
        }
        if(Substudents.size() == 0){
            return Result.fail("本班没有人提交入校申请");
        }
        return Result.succ("获取数据成功",Subnum,Substudents);
    }

    //院系前n个提交入校申请最多的学生
    @GetMapping("/dept/nmostSubmit")
    public Result getdeptMostSubmit(@RequestParam String adminId , @RequestParam Integer n) {
        Department department = departmentService.getDepartByAdmin(adminId); //获取所有的院系
        List<Student> studntList = studentService.getByDepart(department.getDeptId());//获取院系的所有学生
        int stuNum =studntList.size();
        if(stuNum < n){
            n =stuNum;
        }
        List<Student> Substudents = new ArrayList<>();
        List<Integer> num = new ArrayList<>();
        List<Integer> Subnum = new ArrayList<>();
        List<Integer> indexs = new ArrayList<>();
        for(Student student:studntList){
            String id = student.getStuId();
            List<CominApprove> cominApproveList = cominApproveService.getCAByStuId(id);
            num.add(cominApproveList.size());
        }
        System.out.println(studntList);
        System.out.println(num);
        int old = 0;
        for(int i=0; i < n;i++){
            Integer max = Collections.max(num);
            if(max == 0){
                break;
            }
            int index = num.indexOf(max);
            if( index == 0 && i == 0 ){
                indexs.add(index);
                Subnum.add(max);
                num.remove(index);
                old = index;
            }else {
                if(old > index){
                    index = index + i - 1;
                    indexs.add(index);
                    Subnum.add(max);
                    num.remove(index-i+1);
                    old = index-i+1;
                    continue;
                }
                index = index + i;
                indexs.add(index);
                Subnum.add(max);
                num.remove(index-i);
                old = index-i;
            }
        }
        System.out.println(indexs);
        for(Integer theIndex:indexs){
            Substudents.add(studntList.get(theIndex));
        }
        if(Substudents.size() == 0){
            return Result.fail("本院没有人提交入校申请");
        }
        return Result.succ("获取数据成功",Subnum,Substudents);
    }

    @GetMapping("/school/nmostSubmit")
    public Result getschoolMostSubmit(@RequestParam Integer n) {
        List<Student> studntList = studentService.getAllStudent();//获取院系的所有学生
        int stuNum =studntList.size();
        if(stuNum < n){
            n =stuNum;
        }
        List<Student> Substudents = new ArrayList<>();
        List<Integer> num = new ArrayList<>();
        List<Integer> Subnum = new ArrayList<>();
        List<Integer> indexs = new ArrayList<>();
        for(Student student:studntList){
            String id = student.getStuId();
            List<CominApprove> cominApproveList = cominApproveService.getCAByStuId(id);
            num.add(cominApproveList.size());
        }
        System.out.println(studntList);
        System.out.println(num);
        int old = 0;
        for(int i=0; i < n;i++){
            Integer max = Collections.max(num);
            if(max == 0){
                break;
            }
            int index = num.indexOf(max);
            if( index == 0 && i == 0 ){
                indexs.add(index);
                Subnum.add(max);
                num.remove(index);
                old = index;
            }else {
                if(old > index){
                    index = index + i - 1;
                    indexs.add(index);
                    Subnum.add(max);
                    num.remove(index-i+1);
                    old = index-i+1;
                    continue;
                }
                index = index + i;
                indexs.add(index);
                Subnum.add(max);
                num.remove(index-i);
                old = index-i;
            }
        }
        System.out.println(indexs);
        for(Integer theIndex:indexs){
            Substudents.add(studntList.get(theIndex));
        }
        if(Substudents.size() == 0){
            return Result.fail("本校没有人提交入校申请");
        }
        return Result.succ("获取数据成功",Subnum,Substudents);
    }
}


