package com.berhan.repository;

import com.berhan.repository.entity.Kiralama;
import com.berhan.repository.entity.Kisi;
import com.berhan.utility.MyFactoryRepository;

public class KiralamaRepository extends MyFactoryRepository<Kiralama,Long> {
    public KiralamaRepository() {
        super(new Kiralama());
    }
}
