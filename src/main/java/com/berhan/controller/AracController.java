package com.berhan.controller;

import com.berhan.repository.entity.Arac;
import com.berhan.service.AracServis;

import java.util.List;
import java.util.Optional;

public class AracController {
    AracServis aracServis;

    public AracController() {
        this.aracServis = new AracServis();
    }
    public Arac save(Arac entity){
    return aracServis.save(entity);
   }
    public Optional<Arac> findById(Long id){
        return aracServis.findById(id);
    }

    public List<Arac> findAll(){
        return aracServis.findAll();
    }

    public Arac update(Arac entity){
        return aracServis.update(entity);
    }

}
