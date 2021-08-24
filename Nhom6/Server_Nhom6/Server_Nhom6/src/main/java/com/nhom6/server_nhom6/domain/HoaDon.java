package com.nhom6.server_nhom6.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nhom6.server_nhom6.common.domain.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "HoaDon")
public class HoaDon extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long maHoaDon;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(nullable = false)
	private LocalDate ngayThue;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(nullable = false)
	private LocalDate ngayPhaiTra;
	@Column(name = "tongTien")
	private double tongTien;

	@Column(name = "maKhachHang")
	private long maKhachHang;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "maKhachHang",insertable = false,updatable = false)
	private KhachHang khachHang;


	@OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL)
	private List<ChiTietHoaDon> dsChiTietHoaHon = new ArrayList<>();



	public HoaDon(LocalDate ngayThue, LocalDate ngayPhaiTra, double tongTien, long maKhachHang) {
		this.ngayThue = ngayThue;
		this.ngayPhaiTra = ngayPhaiTra;
		this.tongTien = tongTien;
		this.maKhachHang = maKhachHang;
	}

	public HoaDon(double tongTien, long maKhachHang) {
		this.tongTien = tongTien;
		this.maKhachHang = maKhachHang;
	}

	public HoaDon(long maKhachHang) {
		this.maKhachHang = maKhachHang;
	}
}
