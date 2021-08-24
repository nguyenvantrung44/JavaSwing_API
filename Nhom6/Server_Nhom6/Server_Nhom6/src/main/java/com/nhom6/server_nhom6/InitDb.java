package com.nhom6.server_nhom6;

import com.nhom6.server_nhom6.domain.*;
import com.nhom6.server_nhom6.repository.*;
import com.nhom6.server_nhom6.status.TenLoaiDia;
import com.nhom6.server_nhom6.status.TrangThaiDia;
import com.nhom6.server_nhom6.status.TrangThaiKhachHang;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class InitDb implements ApplicationRunner {
    private final TieuDeRepository tieuDeRepository;
    private final LoaiDiaRepository loaiDiaRepository;
    private final DiaRepository diaRepository;
    private final KhachHangRepository khachHangRepository;
    private final RoleRepository roleRepository;
    private final TaiKhoanRepository taiKhoanRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        init1();
        init2();
    }

    private void init1() {
        LoaiDia loaiDia = loaiDiaRepository.save(new LoaiDia(TenLoaiDia.DVD,10000,2000,7));
        LoaiDia loaiDia2 = loaiDiaRepository.save(new LoaiDia(TenLoaiDia.GAME,10000,2000,7));

       TieuDe tieuDe = tieuDeRepository.save(new TieuDe("Tom & Jerry","Hoạt hình thiếu nhi...",1));
        TieuDe tieuDe2 = tieuDeRepository.save(new TieuDe("Con nai vàng","Con nai vàng ngơ ngác...",2));
        diaRepository.save(new Dia(1, TrangThaiDia.sanSangChoThue,1));
        diaRepository.save(new Dia(2, TrangThaiDia.sanSangChoThue,1));
        diaRepository.save(new Dia(3, TrangThaiDia.sanSangChoThue,1));
        diaRepository.save(new Dia(4, TrangThaiDia.sanSangChoThue,2));
        diaRepository.save(new Dia(5, TrangThaiDia.sanSangChoThue,2));
        diaRepository.save(new Dia(6, TrangThaiDia.sanSangChoThue,2));
    }

    private void init2() {

        khachHangRepository.save(new KhachHang("Thanh Bui","Ben Cat","0564848946"));
        khachHangRepository.save(new KhachHang("Trung","Khánh Hòa","0911982090"));
        khachHangRepository.save(new KhachHang("Toàn","Long Khánh","0935704389"));
        khachHangRepository.save(new KhachHang("Oanh","Long Khánh","0935704389"));
        Role role = new Role(1,"ROLE_ADMIN","ADMIN");
        roleRepository.save(role);
        taiKhoanRepository.save(new TaiKhoan(1,"admin","admin",role));
    }

}
