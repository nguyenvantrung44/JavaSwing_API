package com.nhom6.server_nhom6.service.impl;

import com.nhom6.server_nhom6.common.dto.Page.PhiTreHenPage;
import com.nhom6.server_nhom6.common.dto.Page.PhieuDatTruocPage;
import com.nhom6.server_nhom6.common.dto.Page.TieuDePage;
import com.nhom6.server_nhom6.common.dto.PhieuDatTruocDto;
import com.nhom6.server_nhom6.common.dto.Post.PostPhieuDatTruoc;
import com.nhom6.server_nhom6.common.dto.TieuDeDto;
import com.nhom6.server_nhom6.common.exception.ResourceNotFoundException;
import com.nhom6.server_nhom6.common.page.PaginationMeta;
import com.nhom6.server_nhom6.domain.KhachHang;
import com.nhom6.server_nhom6.domain.PhieuDatTruoc;
import com.nhom6.server_nhom6.domain.TieuDe;
import com.nhom6.server_nhom6.repository.KhachHangRepository;
import com.nhom6.server_nhom6.repository.PhieuDatTruocRepository;
import com.nhom6.server_nhom6.repository.Query.PhieuDatTruocDsl;
import com.nhom6.server_nhom6.service.PhieuDatTruocService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhieuDatTruocImpl implements PhieuDatTruocService {

    private final KhachHangRepository khachHangRepository;
    private final PhieuDatTruocRepository phieuDatTruocRepository;
    private final PhieuDatTruocDsl phieuDatTruocDsl;

    @Override
    public long insertPhieuDatTruoc(PostPhieuDatTruoc postPhieuDatTruoc) {

        KhachHang khachHang = khachHangRepository.findById(postPhieuDatTruoc.getMakhachHang()).orElseThrow(() ->{
           throw new ResourceNotFoundException("Ma Khach Hang is not exits !!");
        });

       PhieuDatTruoc phieuDatTruoc = phieuDatTruocRepository.save(new PhieuDatTruoc(khachHang));

        return phieuDatTruoc.getMaPhieuDatTruoc();
    }


    @Override
    public PhieuDatTruocPage searchPhieuDatTruoc(Long id, Pageable pageable) {

        List<PhieuDatTruocDto> list = new ArrayList<>();
        Page<PhieuDatTruoc> page = phieuDatTruocDsl.getPhieuDatTruocByIdKH(id,pageable);
        page.getContent().stream().filter(phieuDatTruoc -> phieuDatTruoc.isActive()).forEach(phieuDatTruoc -> {
            list.add(new PhieuDatTruocDto(phieuDatTruoc.getMaPhieuDatTruoc(),phieuDatTruoc.getCreatedDate().toLocalDateTime().toLocalDate(),phieuDatTruoc.getKhachHang().getMaKhachHang(),phieuDatTruoc.isActive() ));
        });

        PaginationMeta paginationMeta =PaginationMeta.createPagination(page);
        return new PhieuDatTruocPage(paginationMeta,list);
    }

    @Override
    public PhieuDatTruocPage getAllPhieuDatTruouc(Pageable pageable) {
        List<PhieuDatTruocDto> list =new ArrayList<>();
        Page<PhieuDatTruoc> page =phieuDatTruocDsl.getAllPhieuDatTruoc(pageable);
        page.getContent().stream().filter(phieuDatTruoc -> phieuDatTruoc.isActive()).forEach(phieuDatTruoc -> {
            list.add(new PhieuDatTruocDto(phieuDatTruoc));
        });
        PaginationMeta paginationMeta =PaginationMeta.createPagination(page);
        return new PhieuDatTruocPage(paginationMeta,list);
    }
}
