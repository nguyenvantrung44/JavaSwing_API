package com.nhom6.server_nhom6.service;

import com.nhom6.server_nhom6.common.dto.LoaiDiaDto;
import com.nhom6.server_nhom6.common.dto.Post.PostLoaiDia;

import java.util.List;

public interface LoaiDiaService {
    List<LoaiDiaDto> getAll();
    Long insertLoaiDia(PostLoaiDia postLoaiDia);
    LoaiDiaDto searchLoaiDiaById(long id);
}
