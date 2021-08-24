package com.nhom6.server_nhom6.service;

import com.nhom6.server_nhom6.common.dto.Page.ChiTietPhieuDatTruocPage;
import com.nhom6.server_nhom6.common.dto.Page.ChiTietTreHenPage;
import com.nhom6.server_nhom6.common.dto.Post.PostChiTietTreHen;
import com.nhom6.server_nhom6.common.dto.Post.PostPhiTreHen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface ChiTietTreHenService {

        Long insertChiTietTreHen(PostChiTietTreHen postChiTietTreHen);
        ChiTietTreHenPage searchChiTietPhiTreHen(@Param("id") Long id, Pageable pageable);
}
