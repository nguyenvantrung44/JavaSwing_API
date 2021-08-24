package com.nhom6.server_nhom6.service;

import com.nhom6.server_nhom6.common.dto.Page.PhiTreHenPage;
import com.nhom6.server_nhom6.common.dto.Page.PhieuDatTruocPage;
import com.nhom6.server_nhom6.common.dto.Page.TieuDePage;
import com.nhom6.server_nhom6.common.dto.PhieuDatTruocDto;
import com.nhom6.server_nhom6.common.dto.Post.PostPhieuDatTruoc;
import com.nhom6.server_nhom6.domain.PhieuDatTruoc;
import org.springframework.data.domain.Pageable;

public interface PhieuDatTruocService {
    long insertPhieuDatTruoc(PostPhieuDatTruoc postPhieuDatTruocs);
    PhieuDatTruocPage searchPhieuDatTruoc(Long id, Pageable pageable);
    PhieuDatTruocPage getAllPhieuDatTruouc(Pageable pageable);

}
