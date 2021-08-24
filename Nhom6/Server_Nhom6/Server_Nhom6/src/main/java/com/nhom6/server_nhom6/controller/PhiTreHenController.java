package com.nhom6.server_nhom6.controller;

import com.nhom6.server_nhom6.common.dto.KhachHangDto;
import com.nhom6.server_nhom6.common.dto.Page.PhiTreHenPage;

import com.nhom6.server_nhom6.common.dto.PhiTreHenDto;
import com.nhom6.server_nhom6.common.dto.Post.PostPhiTreHen;
import com.nhom6.server_nhom6.service.PhiTreHenService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/nhom6/phitrehen")
@RequiredArgsConstructor
public class PhiTreHenController {

        private  final PhiTreHenService phiTreHenService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PhiTreHenPage> getAllPhiTreHen(@PageableDefault(size = 40,page = 0,direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(phiTreHenService.getAllPhiTreHen(pageable));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> addPhiTreHen(@RequestBody PostPhiTreHen postPhiTreHen){
        return ResponseEntity.ok(phiTreHenService.insertPhiTreHen(postPhiTreHen));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> deletePhiTreHen(@PathVariable("id") Long id){
        return ResponseEntity.ok(phiTreHenService.deletePhiTreHen(id));
    }

    @GetMapping("/kh/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PhiTreHenPage> getPhiTreHenByIdKH(@PageableDefault(size = 40,page = 0,direction = Sort.Direction.ASC) Pageable pageable,@PathVariable("id") long id){
        return ResponseEntity.ok(phiTreHenService.searchPhiTreHen(id,pageable));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> updatePhiTreHen(@PathVariable("id") Long id, @RequestBody PhiTreHenDto phiTreHenDto){
        return ResponseEntity.ok(phiTreHenService.updatePhiTreHen(id,phiTreHenDto));
    }

}
