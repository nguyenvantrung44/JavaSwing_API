package com.nhom6.server_nhom6.common.dto;

import com.nhom6.server_nhom6.domain.ChiTietHoaDon;
import com.nhom6.server_nhom6.domain.ChiTietPhiTreHen;
import com.nhom6.server_nhom6.domain.Dia;

import com.nhom6.server_nhom6.domain.HoaDon;
import com.nhom6.server_nhom6.status.TrangThaiDia;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@Setter
@ToString(of = {"maDia","trangThai","ngayThue","ngayPhaiTra","maKhachHang"})
public class DiaDto {


	private long maDia;
	private String trangThai;


	private  String tenTieuDe;

	private List<ChiTietHoaDonDto> dsChiTietHoaHon;

//	private List<ChiTietPhiTreHen> dsChiTietPhiTreHen = new ArrayList<>();

	private List<HoaDonDto> hoaDonDtos;


	private LocalDate ngayThue;
	private LocalDate ngayPhaiTra;
	private Long maKhachHang;
	private Long maTieuDe;

	public DiaDto(long maDia, String tenTieuDe, LocalDate ngayThue, LocalDate ngayPhaiTra, Long maKhachHang,Long maTieuDe) {
		this.maDia = maDia;
		this.tenTieuDe = tenTieuDe;
		this.ngayThue = ngayThue;
		this.ngayPhaiTra = ngayPhaiTra;
		this.maKhachHang = maKhachHang;
		this.maTieuDe=maTieuDe;
	}

	public DiaDto(Dia dia) {
		this.maDia = dia.getMaDia();
		this.tenTieuDe = dia.getTieuDe().getTenTieuDe();
		this.trangThai = dia.getTrangThai().toString().trim();
	}

	public DiaDto(long maDia, String trangThai, String tenTieuDe) {
		this.maDia = maDia;
		this.trangThai = trangThai;
		this.tenTieuDe = tenTieuDe;
	}

	public DiaDto(long maDia, String tenTieuDe, List<HoaDonDto> hoaDonDtos) {
		this.maDia = maDia;
		this.tenTieuDe = tenTieuDe;
		this.hoaDonDtos = hoaDonDtos;
	}



}
