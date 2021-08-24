package com.nhom6.server_nhom6.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nhom6.server_nhom6.domain.ChiTietPhieuDatTruoc;
import com.nhom6.server_nhom6.domain.LoaiDia;
import com.nhom6.server_nhom6.domain.TieuDe;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
public class TieuDeDto {

	private long maTieuDe;

	private String tenTieuDe;

	private String tomTat;


	private long maLoaiDia;

//	@JsonIgnore
//	private List<DiaDto> dsDia = new ArrayList<>();

//	@JsonIgnore
//	private List<ChiTietPhieuDatTruoc> dsChiTietPhieuDatTruoc = new ArrayList<>();

	private boolean active = true;

	public TieuDeDto(TieuDe tieuDe) {
		this.maTieuDe = tieuDe.getMaTieuDe();
		this.tenTieuDe = tieuDe.getTenTieuDe();
		this.tomTat = tieuDe.getTomTat();
		this.maLoaiDia = tieuDe.getMaLoaiDia();
	}

	public TieuDeDto(long maTieuDe) {
		this.maTieuDe = maTieuDe;
	}
}
