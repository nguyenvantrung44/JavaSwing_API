package com.nhom6.server_nhom6.repository.Query;

import com.nhom6.server_nhom6.domain.TieuDe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface TieuDeQueryDsl {
    Page<TieuDe> getAll(Pageable pageable);
    Page<TieuDe> getIDByName(@Param("tenTieuDe") String tenTieude, Pageable pageable);
}
