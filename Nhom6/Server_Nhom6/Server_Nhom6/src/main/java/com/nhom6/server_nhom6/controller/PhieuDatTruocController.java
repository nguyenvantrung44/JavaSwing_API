package com.nhom6.server_nhom6.controller;


import com.nhom6.server_nhom6.common.dto.Page.PhiTreHenPage;
import com.nhom6.server_nhom6.common.dto.Page.PhieuDatTruocPage;
import com.nhom6.server_nhom6.common.dto.Page.TieuDePage;
import com.nhom6.server_nhom6.common.dto.Post.PostPhieuDatTruoc;
import com.nhom6.server_nhom6.service.PhieuDatTruocService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/nhom6/phieudattruoc")
@RequiredArgsConstructor
public class PhieuDatTruocController {

    private final PhieuDatTruocService phieuDatTruocService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> addPhieuDatTruoc(@RequestBody PostPhieuDatTruoc postPhieuDatTruoc){
        return ResponseEntity.ok(phieuDatTruocService.insertPhieuDatTruoc(postPhieuDatTruoc));
    }

    @GetMapping("/kh/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PhieuDatTruocPage> getPhiTreHenByIdKH(@PageableDefault(size = 40,page = 0,direction = Sort.Direction.ASC) Pageable pageable, @PathVariable("id") long id){
        return ResponseEntity.ok(phieuDatTruocService.searchPhieuDatTruoc(id,pageable));
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PhieuDatTruocPage>getAllPhieuDatTruoc(@PageableDefault(size = 40,page = 0,direction = Sort.Direction.ASC)Pageable pageable){
        return ResponseEntity.ok(phieuDatTruocService.getAllPhieuDatTruouc(pageable));
    }
}
