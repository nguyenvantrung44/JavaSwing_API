package com.nhom6.server_nhom6.common.dto;

import lombok.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString(of ={"email","password"})
public class TaiKhoanDto {

	private String email;
	private String password;

}
