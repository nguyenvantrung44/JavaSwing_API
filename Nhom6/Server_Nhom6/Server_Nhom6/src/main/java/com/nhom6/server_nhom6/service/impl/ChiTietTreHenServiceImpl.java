package com.nhom6.server_nhom6.service.impl;

import com.nhom6.server_nhom6.common.dto.ChiTietPhiTreHenDto;
import com.nhom6.server_nhom6.common.dto.ChiTietPhieuDatTruocDto;
import com.nhom6.server_nhom6.common.dto.Page.ChiTietPhieuDatTruocPage;
import com.nhom6.server_nhom6.common.dto.Page.ChiTietTreHenPage;
import com.nhom6.server_nhom6.common.dto.Post.PostChiTietTreHen;
import com.nhom6.server_nhom6.common.dto.Post.PostPhiTreHen;
import com.nhom6.server_nhom6.common.page.PaginationMeta;
import com.nhom6.server_nhom6.domain.*;
import com.nhom6.server_nhom6.repository.ChiTietPhiTreHenRepository;
import com.nhom6.server_nhom6.repository.DiaRepository;
import com.nhom6.server_nhom6.repository.PhiTreHenRepository;
import com.nhom6.server_nhom6.repository.Query.ChiTietPhiTreHenDsl;
import com.nhom6.server_nhom6.service.ChiTietTreHenService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChiTietTreHenServiceImpl implements ChiTietTreHenService {

    private final DiaRepository diaRepository;

    private final PhiTreHenRepository phiTreHenRepository;

    private final ChiTietPhiTreHenRepository chiTietPhiTreHenRepository;

    private final ChiTietPhiTreHenDsl chiTietPhiTreHenDsl;

    @Override
    public Long insertChiTietTreHen(PostChiTietTreHen postChiTietTreHen) {

        ChiTietPhiTreHen entity = new ChiTietPhiTreHen();
        ChiTietPhiTreHenID key = new ChiTietPhiTreHenID();

        entity.setChiTietPhiTreHenID(key);

        Dia dia = diaRepository.findById(postChiTietTreHen.getMaDia()).get();
        entity.setDia(dia);

        PhiTreHen phiTreHen = phiTreHenRepository.findById(postChiTietTreHen.getMaTreHen()).get();
        entity.setPhiTreHen(phiTreHen);

        entity.setSoNgayTreHen(postChiTietTreHen.getSoNgayTreHen());
        entity.setNgayTra(postChiTietTreHen.getNgayTra());
        entity.setPhiTre(postChiTietTreHen.getPhiTre());

        chiTietPhiTreHenRepository.save(entity);

        return entity.getPhiTreHen().getMaPhiTreHen();
    }

    @Override
    public ChiTietTreHenPage searchChiTietPhiTreHen(Long id, Pageable pageable) {
        List<ChiTietPhiTreHenDto> list = new ArrayList<>();
        Page<ChiTietPhiTreHen> page = chiTietPhiTreHenDsl.getChiTietPTId(id,pageable);
        page.getContent().stream().filter(chiTietPhiTreHen -> chiTietPhiTreHen.getPhiTreHen().isActive()).forEach(chiTietPhiTreHen -> {
            list.add(new ChiTietPhiTreHenDto(chiTietPhiTreHen.getChiTietPhiTreHenID().getMaDia(), chiTietPhiTreHen.getChiTietPhiTreHenID().getMaPhiTreHen(), chiTietPhiTreHen.getPhiTre(),
                    chiTietPhiTreHen.getNgayTra(),chiTietPhiTreHen.getSoNgayTreHen()
                    ));
        });

       // ChiTietPhiTreHenDto(long maDia, long maphiTreHen, double phiTre, LocalDate ngayTra, int soNgayTreHen)
        PaginationMeta paginationMeta =PaginationMeta.createPagination(page);
        return new ChiTietTreHenPage(paginationMeta,list);
    }


}
