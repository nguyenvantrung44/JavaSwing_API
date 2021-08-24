package com.nhom6.server_nhom6.controller;

import com.nhom6.server_nhom6.common.dto.Page.ChiTietPhieuDatTruocPage;
import com.nhom6.server_nhom6.common.dto.Page.ChiTietTreHenPage;
import com.nhom6.server_nhom6.common.dto.Post.PostChiTietHoaDon;
import com.nhom6.server_nhom6.common.dto.Post.PostChiTietTreHen;
import com.nhom6.server_nhom6.service.ChiTietTreHenService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/nhom6/chitietphitrehen")
@RequiredArgsConstructor
public class ChiTietPhiTreHenController {

    private final ChiTietTreHenService chiTietTreHenService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> addChiTietPhiTreHen(@RequestBody PostChiTietTreHen postChiTietTreHen){
        return ResponseEntity.ok(chiTietTreHenService.insertChiTietTreHen(postChiTietTreHen));
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ChiTietTreHenPage> getChiTietTreHenById(@PathVariable("id") long id, @PageableDefault(size = 10,page = 0,direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(chiTietTreHenService.searchChiTietPhiTreHen(id,pageable));
    }
}
