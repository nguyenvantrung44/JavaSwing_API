package com.nhom6.server_nhom6.service.impl;

import com.nhom6.server_nhom6.common.dto.ChiTietHoaDonDto;
import com.nhom6.server_nhom6.common.dto.DiaDto;
import com.nhom6.server_nhom6.common.dto.HoaDonDto;
import com.nhom6.server_nhom6.common.dto.Page.DiaPage;
import com.nhom6.server_nhom6.common.dto.Post.PostDia;
import com.nhom6.server_nhom6.common.exception.ResourceNotFoundException;
import com.nhom6.server_nhom6.common.page.PaginationMeta;
import com.nhom6.server_nhom6.domain.ChiTietHoaDon;
import com.nhom6.server_nhom6.domain.Dia;
import com.nhom6.server_nhom6.domain.HoaDon;
import com.nhom6.server_nhom6.domain.TieuDe;
import com.nhom6.server_nhom6.repository.DiaRepository;
import com.nhom6.server_nhom6.repository.Query.DiaQueryDsl;
import com.nhom6.server_nhom6.repository.TieuDeRepository;
import  com.nhom6.server_nhom6.status.TrangThaiDia;
import com.nhom6.server_nhom6.service.DiaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.nhom6.server_nhom6.status.TrangThaiDia.sanSangChoThue;

@Service
@RequiredArgsConstructor
@Log4j2
public class DiaServiceImpl implements DiaService {

    private  final DiaQueryDsl diaQueryDsl;
    private final TieuDeRepository tieuDeRepository;
    private  final DiaRepository diaRepository;

    @Override
    public DiaPage getAllDia(Pageable pageable) {
        List<DiaDto> list = new ArrayList<>();
        //chỗ này có thể dùng  Page<Dia> page = diaRepository.findAll(pageable);
        Page<Dia> page = diaQueryDsl.getAllDia(pageable);
        page.getContent().forEach(dia -> {
            list.add(new DiaDto(dia));
        });


        PaginationMeta paginationMeta = PaginationMeta.createPagination(page);
        return new DiaPage(paginationMeta,list);
    }

    @Override
    public Long insertDia(PostDia postDia) {
        TieuDe tieuDe = tieuDeRepository.findById(postDia.getMatieuDe()).orElseThrow(() -> {
            throw  new ResourceNotFoundException("ID Tieu De Not Found");
        });

        Dia dia = diaRepository.save(new Dia(postDia.getTrangThai(),postDia.getMatieuDe()));

        return dia.getMaDia();
    }

    @Override
    @Transactional
    public Long deleteDia(Long id) {
        Dia dia = diaRepository.findById(id).orElseThrow(() ->{
            throw  new ResourceNotFoundException("Id not found");
        });
        dia.deleteDia();
        return dia.getMaDia();
    }

    @Override
    public DiaDto searchDia(Long id) {
        Dia entity = diaRepository.findById(id).orElseThrow(() ->{
            throw  new ResourceNotFoundException("Id not found");
        });

        if(entity.getTrangThai() == TrangThaiDia.daGan ||
                entity.getTrangThai() == TrangThaiDia.daThue || entity.getTrangThai() == TrangThaiDia.ngungChoThue )
        {
            throw  new ResourceNotFoundException("Dia is not exits");
        }
        return new DiaDto(entity.getMaDia(),entity.getTrangThai().toString().trim(),entity.getTieuDe().getTenTieuDe());
    }

    @Override
    public DiaDto searchDiaHoaDon(Long id) {
        DiaDto diaRs = new DiaDto();
        Dia diaEntity = diaRepository.findById(id).orElseThrow(() ->{
            throw  new ResourceNotFoundException("Id not found");
        });

        if (diaEntity.getTrangThai().toString().equalsIgnoreCase(TrangThaiDia.daThue.toString())){
            diaEntity.getDsChiTietHoaHon().forEach(x->{
                diaRs.setNgayThue(x.getHoaDon().getNgayThue());
                diaRs.setNgayPhaiTra(x.getHoaDon().getNgayPhaiTra());
                diaRs.setMaKhachHang(x.getHoaDon().getMaKhachHang());

            });
            diaRs.setMaDia(diaEntity.getMaDia());
            diaRs.setTenTieuDe(diaEntity.getTieuDe().getTenTieuDe());
            diaRs.setMaTieuDe(diaEntity.getMaTieuDe());
            diaRs.setTrangThai(diaEntity.getTrangThai().toString());
        }


        return diaRs;
    }

    @Override
    @Transactional
    public Long updateTrangThaiDia(Long idDia, TrangThaiDia trangThaiDia) {

        Dia dia = diaRepository.findById(idDia).orElseThrow(() ->{
            throw new ResourceNotFoundException("Id not found");
        });

        dia.updateTrangThaiDia(trangThaiDia);

        return dia.getMaDia();
    }


}
