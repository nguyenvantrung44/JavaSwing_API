package com.nhom6.server_nhom6.common.dto;

import com.nhom6.server_nhom6.domain.ChiTietHoaDon;
import com.nhom6.server_nhom6.domain.Dia;
import com.nhom6.server_nhom6.domain.HoaDon;

import java.time.LocalDate;

public class ChiTietHoaDonDto {


    private LocalDate ngayThue;
    private LocalDate ngayPhaiTra;


    public ChiTietHoaDonDto(ChiTietHoaDon x) {
        this.ngayPhaiTra = x.getHoaDon().getNgayPhaiTra();
        this.ngayThue = x.getHoaDon().getNgayThue();
    }
}
