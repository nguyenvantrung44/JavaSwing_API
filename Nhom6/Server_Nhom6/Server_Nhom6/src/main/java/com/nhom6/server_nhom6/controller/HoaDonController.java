package com.nhom6.server_nhom6.controller;

import com.nhom6.server_nhom6.common.dto.Post.PostHoaDon;
import com.nhom6.server_nhom6.common.dto.Post.PostKhachHang;
import com.nhom6.server_nhom6.service.HoaDonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/nhom6/hoadon")
@RequiredArgsConstructor
public class HoaDonController {

    private final HoaDonService hoaDonService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> addHoaDon(@RequestBody PostHoaDon postHoaDon){
        return ResponseEntity.ok(hoaDonService.insertHoaDon(postHoaDon));
    }
}
