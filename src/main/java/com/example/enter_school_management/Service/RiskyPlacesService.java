package com.example.enter_school_management.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.enter_school_management.Entity.RiskyPlaces;

import java.util.List;

public interface RiskyPlacesService extends IService<RiskyPlaces> {
    List<RiskyPlaces> getAllRP();
}
