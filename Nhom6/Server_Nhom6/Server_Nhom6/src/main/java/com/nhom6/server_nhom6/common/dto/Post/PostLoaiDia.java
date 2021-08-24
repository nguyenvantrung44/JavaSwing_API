package com.nhom6.server_nhom6.common.dto.Post;

import com.nhom6.server_nhom6.status.TenLoaiDia;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLoaiDia {
    private TenLoaiDia tenLoaiDia;
    private double giaThue;
    private double phiTre;
    private int soNgayThue;
}
