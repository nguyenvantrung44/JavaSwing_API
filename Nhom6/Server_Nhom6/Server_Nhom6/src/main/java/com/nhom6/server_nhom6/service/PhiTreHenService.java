package com.nhom6.server_nhom6.service;

import com.nhom6.server_nhom6.common.dto.Page.PhiTreHenPage;
import com.nhom6.server_nhom6.common.dto.PhiTreHenDto;
import com.nhom6.server_nhom6.common.dto.Post.PostPhiTreHen;
import org.springframework.data.domain.Pageable;

public interface PhiTreHenService {

    PhiTreHenPage getAllPhiTreHen(Pageable pageable);
    Long insertPhiTreHen(PostPhiTreHen postPhiTreHen);
    Long deletePhiTreHen(Long id);
    PhiTreHenPage searchPhiTreHen(Long id,Pageable pageable);
    Long updatePhiTreHen(Long id, PhiTreHenDto phiTreHenDto);
}
