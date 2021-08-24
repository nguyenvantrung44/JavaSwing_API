package com.nhom6.server_nhom6.service.impl;

import com.nhom6.server_nhom6.common.dto.Page.PhiTreHenPage;
import com.nhom6.server_nhom6.common.dto.PhiTreHenDto;
import com.nhom6.server_nhom6.common.dto.Post.PostPhiTreHen;
import com.nhom6.server_nhom6.common.exception.ResourceNotFoundException;
import com.nhom6.server_nhom6.common.page.PaginationMeta;
import com.nhom6.server_nhom6.domain.KhachHang;
import com.nhom6.server_nhom6.domain.PhiTreHen;
import com.nhom6.server_nhom6.repository.KhachHangRepository;
import com.nhom6.server_nhom6.repository.PhiTreHenRepository;
import com.nhom6.server_nhom6.repository.Query.PhiTreHenDsl;
import com.nhom6.server_nhom6.service.PhiTreHenService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhiTreHenServiceImpl implements PhiTreHenService {

    private final PhiTreHenDsl phiTreHenDsl;
    private final KhachHangRepository khachHangRepository;
    private final PhiTreHenRepository phiTreHenRepository;

    @Override
    public PhiTreHenPage getAllPhiTreHen(Pageable pageable) {
        List<PhiTreHenDto> list = new ArrayList<>();
        Page<PhiTreHen> page = phiTreHenDsl.getAllPhiTreHen(pageable);
        page.getContent().stream().filter(phiTreHen -> phiTreHen.isActive()).forEach(phiTreHen -> {
            list.add(new PhiTreHenDto(phiTreHen));
        });

        PaginationMeta paginationMeta = PaginationMeta.createPagination(page);
        return new PhiTreHenPage(paginationMeta,list);
    }

    @Override
    public Long insertPhiTreHen(PostPhiTreHen postPhiTreHen) {
        KhachHang khachHang = khachHangRepository.findById(postPhiTreHen.getKhachHang()).orElseThrow(() ->{
            throw  new ResourceNotFoundException("ID Customer not exits !!");
        });

        PhiTreHen phiTreHen = phiTreHenRepository.save(new PhiTreHen(khachHang,postPhiTreHen.getTongPhiTreHen(),postPhiTreHen.getTienDaTra()));

        return phiTreHen.getMaPhiTreHen();
    }

    @Override
    public Long deletePhiTreHen(Long id) {

        PhiTreHen phiTreHen = phiTreHenRepository.findById(id).orElseThrow(() ->{
            throw new ResourceNotFoundException("Id not found");
        });

        phiTreHen.deletePhiTreHen();

        return phiTreHen.getMaPhiTreHen();
    }

    @Override
    public PhiTreHenPage searchPhiTreHen(Long id, Pageable pageable) {
        List<PhiTreHenDto> list = new ArrayList<>();
        Page<PhiTreHen> page = phiTreHenDsl.getPhiTreHenByIdKH(id,pageable);
        page.getContent().stream().filter(phiTreHen -> phiTreHen.isActive()==true).forEach(phiTreHen -> {
            list.add(new PhiTreHenDto(phiTreHen.getMaPhiTreHen(),phiTreHen.getTongPhiTreHen(),phiTreHen.getTienDaTra(),
                    phiTreHen.getKhachHang().getMaKhachHang()));
        });
        PaginationMeta paginationMeta =PaginationMeta.createPagination(page);
        return new PhiTreHenPage(paginationMeta,list);
    }

    @Override
    public Long updatePhiTreHen(Long id, PhiTreHenDto phiTreHenDto) {

        PhiTreHen phiTreHen = phiTreHenRepository.findById(id).orElseThrow(() ->{
            throw  new ResourceNotFoundException("ID not found");
        });

        phiTreHen.updatePhiTreHen(phiTreHenDto);

        phiTreHenRepository.save(phiTreHen);

        return phiTreHen.getMaPhiTreHen();

    }


}
