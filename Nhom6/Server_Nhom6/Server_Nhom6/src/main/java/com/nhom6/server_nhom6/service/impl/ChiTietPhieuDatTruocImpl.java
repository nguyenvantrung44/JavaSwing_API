package com.nhom6.server_nhom6.service.impl;

import com.nhom6.server_nhom6.common.dto.CTPDTByTieuDeId;
import com.nhom6.server_nhom6.common.dto.ChiTietPhieuDatTruocDto;
import com.nhom6.server_nhom6.common.dto.Page.ChiTietPhieuDatTruocPage;
import com.nhom6.server_nhom6.common.dto.Page.PhieuDatTruocPage;
import com.nhom6.server_nhom6.common.dto.PhieuDatTruocDto;
import com.nhom6.server_nhom6.common.exception.ResourceNotFoundException;
import com.nhom6.server_nhom6.common.page.PaginationMeta;
import com.nhom6.server_nhom6.domain.*;
import com.nhom6.server_nhom6.repository.*;
import com.nhom6.server_nhom6.repository.Query.ChiTietPhieuDatTruocDsl;
import com.nhom6.server_nhom6.repository.Query.PhieuDatTruocDsl;
import com.nhom6.server_nhom6.service.ChiTietPhieuDatTruocService;
import com.nhom6.server_nhom6.status.TrangThaiDatTruoc;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChiTietPhieuDatTruocImpl implements ChiTietPhieuDatTruocService {
    private final PhieuDatTruocRepository phieuDatTruocRepository;
    private final TieuDeRepository tieuDeRepository;
    private final KhachHangRepository khachHangRepository;
    private final ChiTietPhieuDatTruocRepository chiTietPhieuDatTruocRepository;
    private final ChiTietPhieuDatTruocDsl chiTietPhieuDatTruocDsl;
    private  final PhieuDatTruocDsl phieuDatTruocDsl;
    private final DiaRepository diaRepository;

    @Override
    public Long insertCTPDT(Long IdPhieuDatTruoc, Long idTieuDe) {
        PhieuDatTruoc phieuDatTruoc = phieuDatTruocRepository.findById(IdPhieuDatTruoc).orElseThrow(() -> {throw new ResourceNotFoundException("id not found! ");
        });
        TieuDe tieuDe = tieuDeRepository.findById(idTieuDe).orElseThrow(() -> {throw  new ResourceNotFoundException("id not found!");});

        ChiTietPhieuDatTruoc chiTietPhieuDatTruoc = new ChiTietPhieuDatTruoc(phieuDatTruoc,tieuDe, TrangThaiDatTruoc.chuaXacNhan);
        chiTietPhieuDatTruocRepository.save(chiTietPhieuDatTruoc);

        return phieuDatTruoc.getMaPhieuDatTruoc();
    }

    @Override
    public ChiTietPhieuDatTruocPage searchChiTietPhieuDatTruoc(Long id, Pageable pageable) {
        List<ChiTietPhieuDatTruocDto> list = new ArrayList<>();
        Page<ChiTietPhieuDatTruoc> page = chiTietPhieuDatTruocDsl.getChiTietPDTId(id,pageable);

        page.getContent().stream().filter(phieuDatTruoc -> phieuDatTruoc.getPhieuDatTruoc().isActive()).forEach(phieuDatTruoc -> {
            if(phieuDatTruoc.getDia()==null){
                list.add(new ChiTietPhieuDatTruocDto(phieuDatTruoc.getChiTietPhieuDatTruocID().getMaPhieuDatTruoc(),phieuDatTruoc.getChiTietPhieuDatTruocID().getMaTuaDe(),phieuDatTruoc.getTrangThai(),"Chưa có đĩa"));
            }else
                list.add(new ChiTietPhieuDatTruocDto(phieuDatTruoc.getChiTietPhieuDatTruocID().getMaPhieuDatTruoc(),phieuDatTruoc.getChiTietPhieuDatTruocID().getMaTuaDe(),phieuDatTruoc.getTrangThai(),phieuDatTruoc.getDia().getMaDia()+""));

        });
        PaginationMeta paginationMeta =PaginationMeta.createPagination(page);
        return new ChiTietPhieuDatTruocPage(paginationMeta,list);
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

    @Override
    public List<CTPDTByTieuDeId> getPhieuDatTruocByTieuDeId(Long tieuDeId) {
        List<CTPDTByTieuDeId> list = new ArrayList<>();
        chiTietPhieuDatTruocRepository.findAll().stream()

                .forEach(x->{
                    if(x.getDia()==null){
                        if(x.getTieuDe().getMaTieuDe()==tieuDeId&& x.getTrangThai().toString().equalsIgnoreCase(TrangThaiDatTruoc.chuaXacNhan.toString())){
                            list.add(new CTPDTByTieuDeId(x.getTieuDe().getMaTieuDe(),x.getTieuDe().getTenTieuDe()
                                    ,x.getPhieuDatTruoc().getKhachHang().getMaKhachHang(),
                                    x.getPhieuDatTruoc().getMaPhieuDatTruoc(),
                                    x.getPhieuDatTruoc().getCreatedDate().toLocalDateTime().toLocalDate(),x.getTrangThai().toString(),"Chưa có"));

                        }
                    }else  if(x.getTieuDe().getMaTieuDe()==tieuDeId&& x.getTrangThai().toString().equalsIgnoreCase(TrangThaiDatTruoc.chuaXacNhan.toString())){
                        list.add(new CTPDTByTieuDeId(x.getTieuDe().getMaTieuDe(),x.getTieuDe().getTenTieuDe()
                                ,x.getPhieuDatTruoc().getKhachHang().getMaKhachHang(),
                                x.getPhieuDatTruoc().getMaPhieuDatTruoc(),
                                x.getPhieuDatTruoc().getCreatedDate().toLocalDateTime().toLocalDate(),x.getTrangThai().toString(),x.getDia().getMaDia()+""));

                    }

                });
        return list;
    }

    @Override
    @Transactional
    public Long thayDoiTrangThai(Long idPhieuDatTruoc,Long idTieuDe, TrangThaiDatTruoc trangThaiDatTruoc, Long maDia) {

        Dia dia = diaRepository.findById(maDia).orElseThrow(() ->{
            throw new ResourceNotFoundException("Id not found");
        });


        chiTietPhieuDatTruocRepository.findAll().forEach(x->{
            if(x.getPhieuDatTruoc().getMaPhieuDatTruoc()==idPhieuDatTruoc&&x.getTieuDe().getMaTieuDe()==idTieuDe){
                x.updateTrangThai(trangThaiDatTruoc,dia);

            }
//            return Long.valueOf(1);
        });
//        chiTietPhieuDatTruoc.updateTrangThai(trangThaiDatTruoc);
//        return chiTietPhieuDatTruoc.getPhieuDatTruoc().getMaPhieuDatTruoc();
        return Long.valueOf(1);
    }


}
