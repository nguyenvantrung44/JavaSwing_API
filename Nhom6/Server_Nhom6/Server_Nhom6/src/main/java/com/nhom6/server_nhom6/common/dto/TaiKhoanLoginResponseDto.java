package com.nhom6.server_nhom6.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoanLoginResponseDto {


    private String email;
    private String token;
}
