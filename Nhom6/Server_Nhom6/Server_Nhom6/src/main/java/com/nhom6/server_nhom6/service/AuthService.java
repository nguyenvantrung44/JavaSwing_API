package com.nhom6.server_nhom6.service;

import com.nhom6.server_nhom6.common.dto.TaiKhoanDto;
import com.nhom6.server_nhom6.common.dto.TaiKhoanLoginResponseDto;

public interface AuthService {
    TaiKhoanLoginResponseDto login(TaiKhoanDto taiKhoanDto);
    void insert(TaiKhoanDto taiKhoanDto);
    Integer loginBT(TaiKhoanDto taiKhoanDto);
}
