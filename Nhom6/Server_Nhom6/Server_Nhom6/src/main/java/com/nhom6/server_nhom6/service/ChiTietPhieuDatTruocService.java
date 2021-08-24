package com.nhom6.server_nhom6.service;

import com.nhom6.server_nhom6.common.dto.CTPDTByTieuDeId;
import com.nhom6.server_nhom6.common.dto.ChiTietPhieuDatTruocDto;
import com.nhom6.server_nhom6.common.dto.Page.ChiTietPhieuDatTruocPage;
import com.nhom6.server_nhom6.common.dto.Page.PhieuDatTruocPage;
import com.nhom6.server_nhom6.domain.Dia;
import com.nhom6.server_nhom6.status.TrangThaiDatTruoc;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChiTietPhieuDatTruocService {
    Long insertCTPDT(Long khachHang,Long tieuDe);
    ChiTietPhieuDatTruocPage searchChiTietPhieuDatTruoc(@Param("id") Long id, Pageable pageable);
    PhieuDatTruocPage getAllPhieuDatTruouc(Pageable pageable);
    List<CTPDTByTieuDeId> getPhieuDatTruocByTieuDeId(Long tieuDeId);
    Long thayDoiTrangThai(Long idPhieuDatTruoc,Long idTieuDe, TrangThaiDatTruoc trangThaiDatTruoc, Long maDia);
}
