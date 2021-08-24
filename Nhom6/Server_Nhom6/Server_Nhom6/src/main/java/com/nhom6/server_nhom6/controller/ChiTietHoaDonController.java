package com.nhom6.server_nhom6.controller;

import com.nhom6.server_nhom6.common.dto.Post.PostChiTietHoaDon;

import com.nhom6.server_nhom6.service.ChiTietHoaDonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/nhom6/chitiethoadon")
@RequiredArgsConstructor
public class ChiTietHoaDonController {

        private final ChiTietHoaDonService chiTietHoaDonService;


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> addChiTietHoaDon(@RequestBody PostChiTietHoaDon postChiTietHoaDon){
        return ResponseEntity.ok(chiTietHoaDonService.insertChiTietHoaDon(postChiTietHoaDon));
    }
}
