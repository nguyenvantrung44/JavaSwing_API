package com.nhom6.server_nhom6.service;

import com.nhom6.server_nhom6.common.dto.Page.TieuDePage;
import com.nhom6.server_nhom6.common.dto.Post.PostTieuDe;
import com.nhom6.server_nhom6.common.dto.SearchIDTieuDe;
import org.springframework.data.domain.Pageable;

public interface TieuDeService {
    TieuDePage getAllTieuDe(Pageable pageable);
    Long insertTieuDe(PostTieuDe postTieuDe);
    Long deleteTieuDe(Long id);

    TieuDePage searchMaTieuDeByName(String tenTieuDe,Pageable pageable);


}
