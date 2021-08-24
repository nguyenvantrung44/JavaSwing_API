package com.nhom6.server_nhom6.controller;

import com.nhom6.server_nhom6.common.dto.TaiKhoanDto;
import com.nhom6.server_nhom6.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("login")
    public Object post(@RequestBody TaiKhoanDto dto) {
        try {

         //   authService.login(dto);
            // Return user information and token
            return new ResponseEntity<Object>(authService.login(dto), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
    }


    @PostMapping("signup")
    public Object register(@RequestBody TaiKhoanDto dto) {
        try {

            authService.insert(dto);
            return new ResponseEntity<Object>("Sign Up Success", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("singin")
    public Integer singin(@RequestBody TaiKhoanDto dto) {
        return authService.loginBT(dto);
    }
    @PostMapping("singup")
    public Integer singup(@RequestBody TaiKhoanDto dto) {
        return authService.loginBT(dto);
    }
}
