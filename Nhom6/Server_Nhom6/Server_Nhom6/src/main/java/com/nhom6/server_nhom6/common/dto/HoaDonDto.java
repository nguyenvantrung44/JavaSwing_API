package com.nhom6.server_nhom6.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nhom6.server_nhom6.domain.ChiTietHoaDon;
import com.nhom6.server_nhom6.domain.Dia;
import com.nhom6.server_nhom6.domain.HoaDon;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



@Getter
@NoArgsConstructor
public class HoaDonDto {


	private long maHoaDon;

	private LocalDate ngayThue;

	private LocalDate ngayPhaiTra;

	private double tongTien;

	private long makhachHang;


	private List<ChiTietHoaDon> dsChiTietHoaHon = new ArrayList<>();





	public HoaDonDto(ChiTietHoaDon x) {
		this.ngayThue = x.getHoaDon().getNgayThue();
		this.ngayPhaiTra = x.getHoaDon().getNgayPhaiTra();
		this.makhachHang = x.getHoaDon().getMaKhachHang();
		// test đi
	}
}
