package com.berhan.repository;

import com.berhan.repository.entity.Kisi;
import com.berhan.utility.MyFactoryRepository;

public class KisiRepository extends MyFactoryRepository<Kisi,Long> {
    public KisiRepository() {
        super(new Kisi());
    }
}
