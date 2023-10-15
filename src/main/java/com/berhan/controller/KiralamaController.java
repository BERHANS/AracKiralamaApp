package com.berhan.controller;

import com.berhan.repository.entity.Kiralama;
import com.berhan.service.KiralamaService;

import java.util.List;

public class KiralamaController {
    KiralamaService kiralamaService;

    public KiralamaController() {
        this.kiralamaService = new KiralamaService();
    }
    public Kiralama kiralamaYap(Kiralama entity){
        return kiralamaService.save(entity);
    }

    public List<Kiralama> findAll(){
        return kiralamaService.findAll();
    }
}
