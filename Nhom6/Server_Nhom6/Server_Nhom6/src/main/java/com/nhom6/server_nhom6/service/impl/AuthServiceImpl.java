package com.nhom6.server_nhom6.service.impl;

import com.nhom6.server_nhom6.common.dto.TaiKhoanDto;
import com.nhom6.server_nhom6.common.dto.TaiKhoanLoginResponseDto;
import com.nhom6.server_nhom6.common.exception.ResourceNotFoundException;
import com.nhom6.server_nhom6.domain.TaiKhoan;
import com.nhom6.server_nhom6.repository.TaiKhoanRepository;
import com.nhom6.server_nhom6.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final TaiKhoanRepository taiKhoanRepository;

    @Override
    public TaiKhoanLoginResponseDto login(TaiKhoanDto taiKhoanDto) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                taiKhoanDto.getEmail(),taiKhoanDto.getPassword()));

        Date date = new Date();

        String token = Jwts.builder()
                        .setSubject(taiKhoanDto.getEmail())
                        .setIssuedAt(date)
                        .setExpiration(new Date(date.getTime() + 86400000L))
                        .signWith(SignatureAlgorithm.HS512, "ABC_EGH")
                        .compact();

        TaiKhoan taiKhoan = taiKhoanRepository.findByEmail(taiKhoanDto.getEmail());

        TaiKhoanLoginResponseDto dto = new TaiKhoanLoginResponseDto();
        dto.setToken(token);
        dto.setEmail(taiKhoan.getEmail());
        return dto;
    }

    @Override
    public void insert(TaiKhoanDto taiKhoanDto) {
        String hased = BCrypt.hashpw(taiKhoanDto.getPassword(), BCrypt.gensalt());

        TaiKhoan entity = new TaiKhoan();
        entity.setEmail(taiKhoanDto.getEmail());
        entity.setPassword(hased);
//        entity.setRoleId(taiKhoanDto.getRoleId());

        taiKhoanRepository.save(entity);

    }

    @Override
    public Integer loginBT(TaiKhoanDto taiKhoanDto) {
        log.info("tai khoan "+ taiKhoanDto);
        TaiKhoan taiKhoanDangNhap = taiKhoanRepository.findByEmail(taiKhoanDto.getEmail());
        if (taiKhoanDangNhap==null){
            return 0;
        }
        if(taiKhoanDangNhap.getPassword().equalsIgnoreCase(taiKhoanDto.getPassword())){
            return 1;
        }
        return 0;
    }
}
