package com.nhom6.server_nhom6.common.dto;

import com.nhom6.server_nhom6.status.TrangThaiDatTruoc;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CTPDTByTieuDeId {
    private Long maTieuDe;
    private String tenTieuDe;
    private Long maKhachHang;
    private Long maPhieuDatTruoc;
    private LocalDate ngayDat;
    private String trangThaiDatTruoc;
    private String maDia;
}
