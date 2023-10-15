package com.berhan.service;

import com.berhan.repository.KisiRepository;
import com.berhan.repository.entity.Kisi;
import com.berhan.utility.MyFactoryService;

public class KisiService extends MyFactoryService<KisiRepository, Kisi,Long> {
    public KisiService() {
        super(new KisiRepository());
    }
}
