package com.berhan.repository.entity;

import com.berhan.enums.EMarka;
import com.berhan.enums.EModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_arac")
public class Arac {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private EMarka marka;
    @Enumerated(EnumType.STRING)
    private EModel model;
    private Boolean durum;
    @Column(nullable = false,unique = true)
    String plaka;
    @OneToMany(mappedBy = "arac",cascade = CascadeType.ALL)
    List<Kiralama> kiralamaList;


}

