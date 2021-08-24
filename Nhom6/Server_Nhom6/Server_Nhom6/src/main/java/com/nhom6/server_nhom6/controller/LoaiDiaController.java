package com.nhom6.server_nhom6.controller;

import com.nhom6.server_nhom6.common.dto.LoaiDiaDto;
import com.nhom6.server_nhom6.common.dto.Post.PostLoaiDia;
import com.nhom6.server_nhom6.service.LoaiDiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/nhom6/loaidia")
@RequiredArgsConstructor
public class LoaiDiaController {
    private final LoaiDiaService loaiDiaService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<LoaiDiaDto>> getAll(){
        return ResponseEntity.ok(loaiDiaService.getAll());
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> addLoaiDia(@RequestBody PostLoaiDia postLoaiDia ){
        return ResponseEntity.ok(loaiDiaService.insertLoaiDia(postLoaiDia));
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> searchLoaiDia(@PathVariable("id") Long id){
        return ResponseEntity.ok(loaiDiaService.searchLoaiDiaById(id));
    }

}
