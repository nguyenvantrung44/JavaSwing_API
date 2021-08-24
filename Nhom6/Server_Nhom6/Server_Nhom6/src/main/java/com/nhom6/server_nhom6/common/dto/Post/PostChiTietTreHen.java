package com.nhom6.server_nhom6.common.dto.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostChiTietTreHen {



    private long maDia;
    private long maTreHen;

    private double phiTre;

    private LocalDate ngayTra;

    private int soNgayTreHen;
}
