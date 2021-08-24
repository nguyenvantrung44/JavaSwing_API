package com.nhom6.server_nhom6.service.impl;


import com.nhom6.server_nhom6.common.dto.Post.PostHoaDon;

import com.nhom6.server_nhom6.domain.HoaDon;
import com.nhom6.server_nhom6.repository.HoaDonRepository;
import com.nhom6.server_nhom6.service.HoaDonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class HoaDonServiceImpl implements HoaDonService {

    private final HoaDonRepository hoaDonRepository;

    @Override
    public Long insertHoaDon(PostHoaDon postHoaDon) {

        HoaDon hoaDon = hoaDonRepository.save(new HoaDon(postHoaDon.getNgayThue(),postHoaDon.getNgayPhaiTra(),postHoaDon.getTongTien(),postHoaDon.getKhachHang()));
        return hoaDon.getMaHoaDon();
    }


}
