package com.nhom6.server_nhom6.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nhom6.server_nhom6.common.domain.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Optional;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "ChiTietHoaDon")
@Setter
public class ChiTietHoaDon {

    @EmbeddedId
    private ChiTietHoaDonID ma;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("maHoaDon")
    @JoinColumn(name = "maHoaDon")
    private HoaDon hoaDon;

  //  @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("maDia")
    @JoinColumn(name = "maDia",insertable = false,updatable = false)
    private Dia dia;



}
