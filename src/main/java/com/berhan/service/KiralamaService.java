package com.berhan.service;

import com.berhan.repository.KiralamaRepository;
import com.berhan.repository.entity.Kiralama;
import com.berhan.utility.MyFactoryService;

public class KiralamaService extends MyFactoryService<KiralamaRepository, Kiralama,Long> {
    public KiralamaService() {
        super(new KiralamaRepository());
    }
}
