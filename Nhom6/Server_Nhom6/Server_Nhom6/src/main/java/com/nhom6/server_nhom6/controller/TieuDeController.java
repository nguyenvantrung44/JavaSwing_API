package com.nhom6.server_nhom6.controller;

import com.nhom6.server_nhom6.common.dto.Page.TieuDePage;
import com.nhom6.server_nhom6.common.dto.Post.PostTieuDe;
import com.nhom6.server_nhom6.common.dto.SearchIDTieuDe;
import com.nhom6.server_nhom6.service.TieuDeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/nhom6/tieude")
@RequiredArgsConstructor
public class TieuDeController {
    private final TieuDeService tieuDeService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TieuDePage>getAllTieuDe(@PageableDefault(size = 40,page = 0,direction = Sort.Direction.ASC)Pageable pageable){
        return ResponseEntity.ok(tieuDeService.getAllTieuDe(pageable));
    }
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> addTieuDe(@RequestBody PostTieuDe postTieuDe){
        return ResponseEntity.ok(tieuDeService.insertTieuDe(postTieuDe));
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deleteTieuDe(@PathVariable("id") Long id){
        return ResponseEntity.ok(tieuDeService.deleteTieuDe(id));
    }

//    @GetMapping("/name")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<Object> searchMaByName(@RequestParam(required = false,name = "tenTieuDe") String tenTieuDe){
//        return ResponseEntity.ok(tieuDeService.searchMaTieuDeByName(tenTieuDe));
//    }

    @GetMapping("/name/{tenTieuDe}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TieuDePage>getIDByNameTieuDe(@PageableDefault(size = 10,page = 0,direction = Sort.Direction.ASC)Pageable pageable, @PathVariable("tenTieuDe") String tenTieuDe){
        return ResponseEntity.ok(tieuDeService.searchMaTieuDeByName(tenTieuDe,pageable));
    }
}
