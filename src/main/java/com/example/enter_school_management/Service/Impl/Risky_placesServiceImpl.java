package com.example.enter_school_management.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.enter_school_management.Entity.Risky_places;
import com.example.enter_school_management.Mapper.Risky_placesMapper;
import com.example.enter_school_management.Service.Risky_placesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Risky_placesServiceImpl extends ServiceImpl<Risky_placesMapper, Risky_places> implements Risky_placesService {
    @Autowired
    Risky_placesMapper risky_placesMapper;

    @Override
    public List<Risky_places> getAllRP(){
        QueryWrapper<Risky_places> risky_placesQueryWrapper = new QueryWrapper<>();
        risky_placesQueryWrapper.isNotNull("rp_id");
        return risky_placesMapper.selectList(risky_placesQueryWrapper);
    }
}
