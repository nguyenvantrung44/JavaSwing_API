package com.nhom6.server_nhom6.common.dto;

import com.nhom6.server_nhom6.domain.ChiTietPhiTreHen;
import com.nhom6.server_nhom6.domain.KhachHang;
import com.nhom6.server_nhom6.domain.PhiTreHen;
import com.nhom6.server_nhom6.status.TrangThaiPhiTreHen;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;



@Getter
@NoArgsConstructor
public class PhiTreHenDto {


	private long maPhiTreHen;

	private double tongPhiTreHen;
	private double tienDaTra;

	private long maKhachHang;

	private TrangThaiPhiTreHen trangThai;


	private List<ChiTietPhiTreHen> dsChiTietPhiTreHen = new ArrayList<>();

	private boolean active;

	public PhiTreHenDto(PhiTreHen phiTreHen) {
		this.maPhiTreHen = phiTreHen.getMaPhiTreHen();
		this.tongPhiTreHen = phiTreHen.getTongPhiTreHen();
		this.tienDaTra = phiTreHen.getTienDaTra();
		this.maKhachHang = phiTreHen.getKhachHang().getMaKhachHang();
	}

	public PhiTreHenDto(long maPhiTreHen, double tongPhiTreHen, double tienDaTra, long maKhachHang) {
		this.maPhiTreHen = maPhiTreHen;
		this.tongPhiTreHen = tongPhiTreHen;
		this.tienDaTra = tienDaTra;
		this.maKhachHang = maKhachHang;
	}

}
