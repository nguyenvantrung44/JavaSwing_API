package com.nhom6.server_nhom6.service;

import com.nhom6.server_nhom6.common.dto.DiaDto;
import com.nhom6.server_nhom6.common.dto.Page.DiaPage;
import com.nhom6.server_nhom6.common.dto.Post.PostDia;
import com.nhom6.server_nhom6.status.TrangThaiDia;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;

public interface DiaService {
        DiaPage getAllDia(Pageable pageable);
        Long insertDia(PostDia postDia);
        Long deleteDia(Long id);
        DiaDto searchDia(Long id);

        DiaDto searchDiaHoaDon(Long id);
        Long updateTrangThaiDia(Long idDia, TrangThaiDia trangThaiDia);

}
