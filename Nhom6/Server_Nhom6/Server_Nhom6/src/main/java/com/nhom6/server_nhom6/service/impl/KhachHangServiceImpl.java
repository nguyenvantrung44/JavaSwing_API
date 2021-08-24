package com.nhom6.server_nhom6.service.impl;


import com.nhom6.server_nhom6.common.dto.KhachHangDto;

import com.nhom6.server_nhom6.common.dto.Page.KhachHangPage;
import com.nhom6.server_nhom6.common.dto.Post.PostKhachHang;
import com.nhom6.server_nhom6.common.exception.ResourceNotFoundException;
import com.nhom6.server_nhom6.common.page.PaginationMeta;

import com.nhom6.server_nhom6.domain.KhachHang;
import com.nhom6.server_nhom6.repository.KhachHangRepository;
import com.nhom6.server_nhom6.repository.Query.KhachHangDsl;
import com.nhom6.server_nhom6.service.KhachHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KhachHangServiceImpl implements KhachHangService {

    private final KhachHangDsl khachHangDsl;
    private final KhachHangRepository khachHangRepository;

    @Override
    public KhachHangPage getAllKhachHang(Pageable pageable) {
        List<KhachHangDto> list = new ArrayList<>();

        Page<KhachHang> page = khachHangDsl.getAllKhachHang(pageable);
        page.getContent().stream().filter(khachHang -> khachHang.isTrangThai()).forEach(khachHang -> {
            list.add(new KhachHangDto(khachHang));
        });

        PaginationMeta paginationMeta = PaginationMeta.createPagination(page);
        return new KhachHangPage(paginationMeta,list);
    }

    @Override
    public Long insertKhachHang(PostKhachHang postKhachHang) {

        KhachHang khachHang = khachHangRepository.save(new KhachHang(postKhachHang.getHoTen(),postKhachHang.getDiaChi(),postKhachHang.getSoDienThoai()));
        return khachHang.getMaKhachHang();
    }

    @Override
    @Transactional
    public Long deleteKhachHang(Long id) {
        KhachHang khachHang = khachHangRepository.findById(id).orElseThrow(() ->{
            throw  new ResourceNotFoundException("Id not found");
        });

        khachHang.deleteKhachHang();
        return khachHang.getMaKhachHang();
    }

    @Override
    public KhachHangDto searchKhachHang(Long id) {

      KhachHang entity = khachHangRepository.findById(id).orElseThrow(() ->{
          throw  new ResourceNotFoundException("Id not found");
      });

      if(entity.isTrangThai() == false){
          throw  new ResourceNotFoundException("Customer is not exits !!");
      }

        return new KhachHangDto(entity.getMaKhachHang(),entity.getHoTen(),entity.getDiaChi(),
                entity.getSoDienThoai());
    }

    @Override
    @Transactional
    public Long editKhachHang(Long id,KhachHangDto khachHangDto) {

        KhachHang entity = khachHangRepository.findById(id).orElseThrow(() -> {
            throw  new ResourceNotFoundException("Id not found");
        });

        entity.updateKhachHang(khachHangDto);



        return entity.getMaKhachHang();
    }
}
