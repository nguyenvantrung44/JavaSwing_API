package com.nhom6.server_nhom6.common.dto.Post;

import com.nhom6.server_nhom6.common.dto.KhachHangDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostHoaDon {

    private long maHoaDon;

    private LocalDate ngayThue;

    private LocalDate ngayPhaiTra;

    private double tongTien;

    private long khachHang;
}
