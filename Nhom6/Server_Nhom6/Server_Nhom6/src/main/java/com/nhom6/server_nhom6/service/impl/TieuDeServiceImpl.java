package com.nhom6.server_nhom6.service.impl;

import com.nhom6.server_nhom6.common.dto.Page.TieuDePage;
import com.nhom6.server_nhom6.common.dto.Post.PostTieuDe;
import com.nhom6.server_nhom6.common.dto.SearchIDTieuDe;
import com.nhom6.server_nhom6.common.dto.TieuDeDto;
import com.nhom6.server_nhom6.common.exception.ResourceNotFoundException;
import com.nhom6.server_nhom6.common.page.PaginationMeta;
import com.nhom6.server_nhom6.domain.Dia;
import com.nhom6.server_nhom6.domain.LoaiDia;
import com.nhom6.server_nhom6.domain.TieuDe;
import com.nhom6.server_nhom6.repository.DiaRepository;
import com.nhom6.server_nhom6.repository.LoaiDiaRepository;
import com.nhom6.server_nhom6.repository.Query.TieuDeQueryDsl;
import com.nhom6.server_nhom6.repository.TieuDeRepository;
import com.nhom6.server_nhom6.service.TieuDeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TieuDeServiceImpl implements TieuDeService {
    private final TieuDeQueryDsl tieuDeQueryDsl;
    private final DiaRepository diaRepository;
    private final LoaiDiaRepository loaiDiaRepository;
    private final TieuDeRepository tieuDeRepository;

    @Override
    public TieuDePage getAllTieuDe(Pageable pageable) {
        List<TieuDeDto> list =new ArrayList<>();
        Page<TieuDe> page =tieuDeQueryDsl.getAll(pageable);
        page.getContent().stream().filter(tieuDe -> tieuDe.isActive()).forEach(tieuDe -> {
            list.add(new TieuDeDto(tieuDe));
        });
        PaginationMeta paginationMeta =PaginationMeta.createPagination(page);
        return new TieuDePage(paginationMeta,list);
    }

    @Override
    public Long insertTieuDe(PostTieuDe postTieuDe) {
//        LoaiDia loaiDia = loaiDiaRepository.findById(postTieuDe.getLoaiDia()).orElseThrow(() -> {
//            throw new ResourceNotFoundException("id not found!");
//        });

        TieuDe tieuDe = tieuDeRepository.save(new TieuDe(postTieuDe.getTenTieuDe(),postTieuDe.getTomTat(),postTieuDe.getLoaiDia()));


        return tieuDe.getMaTieuDe();
    }

    @Override
    @Transactional
    public Long deleteTieuDe(Long id) {
        TieuDe tieuDe = tieuDeRepository.findById(id).orElseThrow(() -> {
           throw  new ResourceNotFoundException("id not found !");
        });
        tieuDe.deleteTieuDe();
        return tieuDe.getMaTieuDe();
    }

    @Override
    public TieuDePage searchMaTieuDeByName(String tenTieuDe,Pageable pageable) {

        List<TieuDeDto> list =new ArrayList<>();
        Page<TieuDe> page =tieuDeQueryDsl.getIDByName(tenTieuDe,pageable);
        page.getContent().stream().filter(tieuDe -> tieuDe.isActive()).forEach(tieuDe -> {
            list.add(new TieuDeDto(tieuDe.getMaTieuDe()));
        });
        PaginationMeta paginationMeta =PaginationMeta.createPagination(page);
        return new TieuDePage(paginationMeta,list);

    }
}
