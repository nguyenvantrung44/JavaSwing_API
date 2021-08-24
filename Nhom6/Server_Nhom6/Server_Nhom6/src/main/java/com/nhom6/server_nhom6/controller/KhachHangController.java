package com.nhom6.server_nhom6.controller;

import com.nhom6.server_nhom6.common.dto.KhachHangDto;

import com.nhom6.server_nhom6.common.dto.Page.KhachHangPage;

import com.nhom6.server_nhom6.common.dto.Post.PostKhachHang;

import com.nhom6.server_nhom6.service.KhachHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/nhom6/khachhang")
@RequiredArgsConstructor
public class KhachHangController {

    private  final KhachHangService khachHangService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<KhachHangPage> getAllKhachHang(@PageableDefault(size = 40,page = 0,direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(khachHangService.getAllKhachHang(pageable));
    }


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> addKhachHang(@RequestBody PostKhachHang postKhachHang){
        return ResponseEntity.ok(khachHangService.insertKhachHang(postKhachHang));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteKhachHang(@PathVariable("id") Long id){
        return ResponseEntity.ok(khachHangService.deleteKhachHang(id));
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> searchKhachHang(@PathVariable("id") Long id){
        return ResponseEntity.ok(khachHangService.searchKhachHang(id));
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> editKhachHang(@PathVariable("id") Long id, @RequestBody KhachHangDto khachHangDto){
        return ResponseEntity.ok(khachHangService.editKhachHang(id,khachHangDto));
    }
}
