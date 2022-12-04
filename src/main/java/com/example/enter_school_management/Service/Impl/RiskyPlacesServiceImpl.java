package com.example.enter_school_management.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enter_school_management.Entity.RiskyPlaces;
import com.example.enter_school_management.Mapper.RiskyPlacesMapper;
import com.example.enter_school_management.Service.RiskyPlacesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiskyPlacesServiceImpl extends ServiceImpl<RiskyPlacesMapper, RiskyPlaces> implements RiskyPlacesService {
    @Autowired
    RiskyPlacesMapper risky_placesMapper;

    @Override
    public List<RiskyPlaces> getAllRP(){
        QueryWrapper<RiskyPlaces> riskyPlacesQueryWrapper = new QueryWrapper<>();
        riskyPlacesQueryWrapper.isNotNull("rp_id");
        return risky_placesMapper.selectList(riskyPlacesQueryWrapper);
    }
}
