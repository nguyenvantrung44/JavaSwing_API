package com.nhom6.server_nhom6.service.impl;

import com.nhom6.server_nhom6.domain.TaiKhoan;
import com.nhom6.server_nhom6.common.dto.UserDetailsDto;
import com.nhom6.server_nhom6.repository.TaiKhoanRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {


    private TaiKhoanRepository taiKhoanRepository;

    public UserDetailServiceImpl(TaiKhoanRepository taiKhoanRepository) {
        this.taiKhoanRepository = taiKhoanRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        TaiKhoan taiKhoan = taiKhoanRepository.findByEmail(email);
        if(taiKhoan == null)
            throw  new UsernameNotFoundException("Account is not extis");

        String roleName = taiKhoan.getRole().getName();
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(roleName));

        return new UserDetailsDto(email,taiKhoan.getPassword(),authorities);
    }
}
