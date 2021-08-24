package com.nhom6.server_nhom6.repository.Query;

import com.nhom6.server_nhom6.domain.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface KhachHangDsl {

        Page<KhachHang> getAllKhachHang(Pageable pageable);

}
