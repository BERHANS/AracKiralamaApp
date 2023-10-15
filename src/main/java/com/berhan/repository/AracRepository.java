package com.berhan.repository;

import com.berhan.repository.entity.Arac;
import com.berhan.repository.entity.Kisi;
import com.berhan.utility.MyFactoryRepository;

public class AracRepository extends MyFactoryRepository<Arac,Long> {
    public AracRepository() {
        super(new Arac());
    }
}
