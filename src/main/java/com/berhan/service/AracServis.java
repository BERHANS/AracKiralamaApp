package com.berhan.service;

import com.berhan.repository.AracRepository;
import com.berhan.repository.entity.Arac;
import com.berhan.utility.MyFactoryService;

public class AracServis extends MyFactoryService<AracRepository, Arac,Long> {
    public AracServis() {
        super(new AracRepository());
    }
}
