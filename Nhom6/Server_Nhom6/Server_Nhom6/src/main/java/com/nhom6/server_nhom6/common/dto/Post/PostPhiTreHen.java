package com.nhom6.server_nhom6.common.dto.Post;

import com.nhom6.server_nhom6.domain.ChiTietPhiTreHen;
import com.nhom6.server_nhom6.domain.KhachHang;
import com.nhom6.server_nhom6.status.TrangThaiPhiTreHen;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostPhiTreHen {


    private long maPhiTreHen;

    private Long khachHang;
    private double tongPhiTreHen;
    private double tienDaTra;

}
