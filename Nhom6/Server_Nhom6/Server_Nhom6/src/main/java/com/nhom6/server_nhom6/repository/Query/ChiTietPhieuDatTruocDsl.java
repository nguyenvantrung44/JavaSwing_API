package com.nhom6.server_nhom6.repository.Query;

import com.nhom6.server_nhom6.domain.ChiTietPhieuDatTruoc;
import com.nhom6.server_nhom6.domain.PhiTreHen;
import com.nhom6.server_nhom6.domain.PhieuDatTruoc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface ChiTietPhieuDatTruocDsl {

    Page<ChiTietPhieuDatTruoc> getChiTietPDTId(@Param("id") long id, Pageable pageable);
}
