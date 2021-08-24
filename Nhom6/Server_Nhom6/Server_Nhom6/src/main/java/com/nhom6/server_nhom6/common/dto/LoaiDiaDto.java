package com.nhom6.server_nhom6.common.dto;

import com.nhom6.server_nhom6.status.TenLoaiDia;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;



@Getter
@NoArgsConstructor
public class LoaiDiaDto {


	private long maLoaiDia;
	private TenLoaiDia tenLoaiDia;
	private double giaThue;
	private double phiTre;
	private int soNgayThue;

	private boolean active = true;

	private List<TieuDeDto> dsTuaDe = new ArrayList<>();

	public LoaiDiaDto(long maLoaiDia, TenLoaiDia tenLoaiDia, double giaThue, double phiTre, int soNgayThue) {
		this.maLoaiDia = maLoaiDia;
		this.tenLoaiDia = tenLoaiDia;
		this.giaThue = giaThue;
		this.phiTre = phiTre;
		this.soNgayThue = soNgayThue;
	}

	public LoaiDiaDto(long maLoaiDia, double giaThue, double phiTre, int soNgayThue) {
		this.maLoaiDia = maLoaiDia;
		this.giaThue = giaThue;
		this.phiTre = phiTre;
		this.soNgayThue = soNgayThue;
	}

	public LoaiDiaDto(double giaThue, int soNgayThue) {
		this.giaThue = giaThue;
		this.soNgayThue = soNgayThue;
	}

	public LoaiDiaDto(long maLoaiDia, double giaThue, int soNgayThue) {
		this.maLoaiDia = maLoaiDia;
		this.giaThue = giaThue;
		this.soNgayThue = soNgayThue;
	}

}
