package com.nhom6.server_nhom6.service.impl;

import com.nhom6.server_nhom6.common.dto.LoaiDiaDto;
import com.nhom6.server_nhom6.common.dto.Post.PostLoaiDia;
import com.nhom6.server_nhom6.common.exception.ResourceNotFoundException;
import com.nhom6.server_nhom6.domain.LoaiDia;
import com.nhom6.server_nhom6.repository.LoaiDiaRepository;
import com.nhom6.server_nhom6.repository.Query.LoaiDiaDsl;
import com.nhom6.server_nhom6.service.LoaiDiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoaiDiaServiceImpl implements LoaiDiaService {
    private final LoaiDiaDsl loaiDiaDsl;
    private final LoaiDiaRepository loaiDiaRepository;

    @Override
    public List<LoaiDiaDto> getAll() {
        List<LoaiDiaDto> loaiDiaDtos = new ArrayList<>();
        loaiDiaDsl.getAll().forEach(x -> {
            loaiDiaDtos.add(new LoaiDiaDto(x.getMaLoaiDia(), x.getTenLoaiDia(), x.getGiaThue(), x.getPhiTre(), x.getSoNgayThue()));
        });
        return loaiDiaDtos;
    }

    @Override
    public Long insertLoaiDia(PostLoaiDia postLoaiDia) {
        LoaiDia loaiDia = loaiDiaRepository.save(new LoaiDia(postLoaiDia.getTenLoaiDia(), postLoaiDia.getGiaThue(), postLoaiDia.getPhiTre(), postLoaiDia.getSoNgayThue()));
        return loaiDia.getMaLoaiDia();
    }

    @Override
    public LoaiDiaDto searchLoaiDiaById(long id) {

        LoaiDia entity = loaiDiaRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException("Id loaidia not found");
        });

        if(entity.isActive() == false){
            throw new ResourceNotFoundException("Id loaidia is not available !!!");
        }

        return new LoaiDiaDto(entity.getMaLoaiDia(),entity.getGiaThue(),entity.getPhiTre(), entity.getSoNgayThue());
    }
}
