package com.nhom6.server_nhom6.repository.Query;

import com.nhom6.server_nhom6.domain.ChiTietPhiTreHen;
import com.nhom6.server_nhom6.domain.ChiTietPhieuDatTruoc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface ChiTietPhiTreHenDsl {

    Page<ChiTietPhiTreHen> getChiTietPTId(@Param("id") long id, Pageable pageable);
}
