package com.berhan.controller;

import com.berhan.repository.entity.Kisi;
import com.berhan.service.KisiService;

import java.util.List;
import java.util.Optional;

public class KisiController {
    KisiService kisiService;

    public KisiController() {
        this.kisiService = new KisiService();
    }
    public Kisi save(Kisi kisi){
        return kisiService.save(kisi);
    }
    public Optional<Kisi> findById(Long id){
        return kisiService.findById(id);
    }
    public List<Kisi> findByColumnNameAndValue(String columnName,String value) {
        return kisiService.findByColumnNameAndValue(columnName, value);
    }
    public List<Kisi> findAll(){
        return kisiService.findAll();
    }
}
