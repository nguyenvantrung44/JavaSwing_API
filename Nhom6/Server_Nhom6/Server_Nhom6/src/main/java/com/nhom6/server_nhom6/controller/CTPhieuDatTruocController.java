package com.nhom6.server_nhom6.controller;

import com.nhom6.server_nhom6.common.dto.CTPDTByTieuDeId;
import com.nhom6.server_nhom6.common.dto.Page.ChiTietPhieuDatTruocPage;
import com.nhom6.server_nhom6.common.dto.Page.PhiTreHenPage;
import com.nhom6.server_nhom6.service.ChiTietPhieuDatTruocService;
import com.nhom6.server_nhom6.status.TrangThaiDatTruoc;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/nhom6/ctpdt")
@RequiredArgsConstructor
public class CTPhieuDatTruocController {
    private final ChiTietPhieuDatTruocService chiTietPhieuDatTruocService;
    @PostMapping("/{idPhieuDatTruoc}/{idTieuDe}")
    public ResponseEntity<Long> datTruoc(@PathVariable("idPhieuDatTruoc") Long idPhieuDatTruoc,@PathVariable("idTieuDe") Long idTieuDe){

        return ResponseEntity.ok(chiTietPhieuDatTruocService.insertCTPDT(idPhieuDatTruoc,idTieuDe));
    }


    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ChiTietPhieuDatTruocPage> getChiTietPhieuDatTruocById(@PathVariable("id") long id,@PageableDefault(size = 10,page = 0,direction = Sort.Direction.ASC)Pageable pageable){
        return ResponseEntity.ok(chiTietPhieuDatTruocService.searchChiTietPhieuDatTruoc(id,pageable));
    }

    @GetMapping("/getAllChitietPhieuDatTruocByTieuDeId/{tieuDeId}")
    public ResponseEntity<List<CTPDTByTieuDeId>> getAllChitietPhieuDatTruocByTieuDeId(@PathVariable("tieuDeId") Long tieuDeId){
        return ResponseEntity.ok(chiTietPhieuDatTruocService.getPhieuDatTruocByTieuDeId(tieuDeId));
    }
    @PutMapping("/thay-doi-trang-thai/{idPhieuDatTruoc}/{idTieuDe}/{maDia}")
    public Long thayDoiTrangThai(@PathVariable("idPhieuDatTruoc") Long idPhieuDatTruoc
            ,@PathVariable("idTieuDe") Long idTieuDe
            ,@RequestParam("trangThai")TrangThaiDatTruoc trangThai
            ,@PathVariable("maDia") Long maDia
    ){
        return chiTietPhieuDatTruocService.thayDoiTrangThai(idPhieuDatTruoc,idTieuDe,trangThai,maDia);
    }
}
