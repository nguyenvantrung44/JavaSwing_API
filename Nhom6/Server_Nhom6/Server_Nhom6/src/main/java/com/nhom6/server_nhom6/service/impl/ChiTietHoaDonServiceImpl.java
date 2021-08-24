package com.nhom6.server_nhom6.service.impl;

import com.nhom6.server_nhom6.common.dto.Post.PostChiTietHoaDon;
import com.nhom6.server_nhom6.domain.ChiTietHoaDon;
import com.nhom6.server_nhom6.domain.ChiTietHoaDonID;
import com.nhom6.server_nhom6.domain.Dia;
import com.nhom6.server_nhom6.domain.HoaDon;
import com.nhom6.server_nhom6.repository.ChiTietHoaDonRepository;
import com.nhom6.server_nhom6.repository.DiaRepository;
import com.nhom6.server_nhom6.repository.HoaDonRepository;
import com.nhom6.server_nhom6.service.ChiTietHoaDonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.OptionalLong;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class ChiTietHoaDonServiceImpl implements ChiTietHoaDonService {

    private final ChiTietHoaDonRepository chiTietHoaDonRepository;

    private final DiaRepository diaRepository;

    private final HoaDonRepository hoaDonRepository;


    @Override
    public Long insertChiTietHoaDon(PostChiTietHoaDon postChiTietHoaDon) {
        ChiTietHoaDon entity = new ChiTietHoaDon();
        ChiTietHoaDonID key = new ChiTietHoaDonID();

        entity.setMa(key);

        
        Dia diaEntity = diaRepository.findById(postChiTietHoaDon.getMaDia()).get();
        entity.setDia(diaEntity);

        HoaDon hoadonEntity = hoaDonRepository.findById(postChiTietHoaDon.getMaHoaDon()).get();
        entity.setHoaDon(hoadonEntity);

        chiTietHoaDonRepository.save(entity);

        diaEntity.daThueDia();


        return hoadonEntity.getMaHoaDon();
    }
}
