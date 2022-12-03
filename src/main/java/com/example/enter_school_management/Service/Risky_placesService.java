package com.example.enter_school_management.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enter_school_management.Entity.Risky_places;

import java.util.List;

public interface Risky_placesService extends IService<Risky_places> {
    List<Risky_places> getAllRP();
}
