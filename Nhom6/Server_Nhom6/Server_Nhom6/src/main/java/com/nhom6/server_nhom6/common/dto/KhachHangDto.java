package com.nhom6.server_nhom6.common.dto;

import com.nhom6.server_nhom6.domain.KhachHang;

import com.nhom6.server_nhom6.status.TrangThaiKhachHang;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;



@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KhachHangDto {

	private long maKhachHang;

	private String hoTen;

	private String diaChi;

	private String soDienThoai;



	private List<HoaDonDto> dsHoaDon = new ArrayList<>();

	private List<PhieuDatTruocDto> dsPhieuDatTruoc = new ArrayList<>();

	private List<PhiTreHenDto> dsPhiTreHen = new ArrayList<>();
	
	private boolean trangThai = true;

	public KhachHangDto(KhachHang khachHang) {
		this.maKhachHang = khachHang.getMaKhachHang();
		this.hoTen = khachHang.getHoTen();
		this.diaChi = khachHang.getDiaChi();
		this.soDienThoai = khachHang.getSoDienThoai();
		this.trangThai=khachHang.isTrangThai();
	}

	public KhachHangDto(long maKhachHang, String hoTen, String diaChi, String soDienThoai, List<PhieuDatTruocDto> dsPhieuDatTruoc, List<PhiTreHenDto> dsPhiTreHen) {
		this.maKhachHang = maKhachHang;
		this.hoTen = hoTen;
		this.diaChi = diaChi;
		this.soDienThoai = soDienThoai;
		this.dsPhieuDatTruoc = dsPhieuDatTruoc;
		this.dsPhiTreHen = dsPhiTreHen;
	}

	public KhachHangDto(long maKhachHang, String hoTen, String diaChi, String soDienThoai) {
		this.maKhachHang = maKhachHang;
		this.hoTen = hoTen;
		this.diaChi = diaChi;
		this.soDienThoai = soDienThoai;
	}
}
