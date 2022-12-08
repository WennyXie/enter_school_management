package com.example.enter_school_management.Controller;

import com.example.enter_school_management.Common.lang.Result;
import com.example.enter_school_management.Entity.RiskyPlaces;
import com.example.enter_school_management.Service.RiskyPlacesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/RP")
public class RiskyPlacesController {
    @Autowired
    RiskyPlacesService riskyPlacesService;

    @PostMapping("/save")
    public Result saveRP(@RequestBody RiskyPlaces riskyPlaces){
        riskyPlaces.setRpId(null);
        riskyPlacesService.save(riskyPlaces);
        return Result.succ("新增风险地区成功");
    }

    @PostMapping("/update")
    public Result updateRP(@RequestBody RiskyPlaces riskyPlaces){
        riskyPlacesService.updateById(riskyPlaces);
        return Result.succ("更新风险地区成功");
    }

    @PostMapping("/delete")
    public Result deleteRP(@RequestParam Long RPId){
        riskyPlacesService.removeById(RPId);
        return Result.succ("删除风险地区成功");
    }
}
