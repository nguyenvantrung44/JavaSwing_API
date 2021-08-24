package com.nhom6.server_nhom6.controller;

import com.nhom6.server_nhom6.common.dto.DiaDto;
import com.nhom6.server_nhom6.common.dto.Page.DiaPage;
import com.nhom6.server_nhom6.common.dto.Post.PostDia;

import com.nhom6.server_nhom6.service.DiaService;
import com.nhom6.server_nhom6.status.TrangThaiDia;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/nhom6/dia")
@RequiredArgsConstructor
public class DiaController {

    private final DiaService diaService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DiaPage> getAllDia(@PageableDefault(size = 40,page = 0,direction = Sort.Direction.ASC)Pageable pageable){
        return ResponseEntity.ok(diaService.getAllDia(pageable));
    }


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> addDia(@RequestBody PostDia postDia){
        return ResponseEntity.ok(diaService.insertDia(postDia));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteDia(@PathVariable("id") Long id){
        return ResponseEntity.ok(diaService.deleteDia(id));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> searchDia(@PathVariable("id") Long id){
        return ResponseEntity.ok(diaService.searchDia(id));
    }

    @GetMapping("/hoadon/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DiaDto> searchDiaHoaDon(@PathVariable("id") Long id){
        return ResponseEntity.ok(diaService.searchDiaHoaDon(id));
    }

    @PutMapping("/updateDia/{idDia}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateTrangThaiDia(@PathVariable("idDia") Long idDia, @RequestParam("trangThai")TrangThaiDia trangThaiDia){
        return ResponseEntity.ok(diaService.updateTrangThaiDia(idDia,trangThaiDia));
    }
}
