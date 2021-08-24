package com.nhom6.server_nhom6.common.dto;

import com.nhom6.server_nhom6.domain.ChiTietPhieuDatTruoc;
import com.nhom6.server_nhom6.domain.PhieuDatTruoc;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
public class PhieuDatTruocDto {

	private long maPhieuDatTruoc;



	protected LocalDate ngayDat;

	private long makhachHang;

	private List<ChiTietPhieuDatTruoc> dsChiTietPhieuDatTruoc = new ArrayList<>();

	private boolean active;

	public PhieuDatTruocDto(long maPhieuDatTruoc, LocalDate ngayDat, long makhachHang, boolean active) {
		this.maPhieuDatTruoc = maPhieuDatTruoc;
		this.ngayDat = ngayDat;
		this.active = active;
		this.makhachHang = makhachHang;
	}

	public PhieuDatTruocDto(PhieuDatTruoc phieuDatTruoc) {
		this.maPhieuDatTruoc = phieuDatTruoc.getMaPhieuDatTruoc();
		this.ngayDat = phieuDatTruoc.getCreatedDate().toLocalDateTime().toLocalDate();
		this.makhachHang = phieuDatTruoc.getKhachHang().getMaKhachHang();
	}
}
