package com.nhom6.server_nhom6.service;

import com.nhom6.server_nhom6.common.dto.KhachHangDto;
import com.nhom6.server_nhom6.common.dto.Page.KhachHangPage;
import com.nhom6.server_nhom6.common.dto.Post.PostKhachHang;

import org.springframework.data.domain.Pageable;

public interface KhachHangService {

        KhachHangPage getAllKhachHang(Pageable pageable);
        Long insertKhachHang(PostKhachHang postKhachHang);
        Long deleteKhachHang(Long id);
        KhachHangDto searchKhachHang(Long id);
        Long editKhachHang(Long id,KhachHangDto khachHangDto);

}
