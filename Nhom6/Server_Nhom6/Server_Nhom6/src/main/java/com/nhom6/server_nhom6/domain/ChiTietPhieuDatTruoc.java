package com.nhom6.server_nhom6.domain;

import com.nhom6.server_nhom6.status.TrangThaiDatTruoc;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.mapping.Set;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "ChiTietPhieuDatTruoc")
public class ChiTietPhieuDatTruoc{

	@EmbeddedId
	private ChiTietPhieuDatTruocID chiTietPhieuDatTruocID = new ChiTietPhieuDatTruocID();
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("maPhieuDatTruoc")
	@JoinColumn(name = "maPhieuDatTruoc")
	private PhieuDatTruoc phieuDatTruoc;
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("maTuaDe")
	@JoinColumn(name = "maTuaDe")
	private TieuDe tieuDe;

	@Column(nullable = false)
	private TrangThaiDatTruoc trangThai;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="maDia", nullable=true)
	private Dia dia;

	public ChiTietPhieuDatTruoc(PhieuDatTruoc phieuDatTruoc, TieuDe tieuDe, TrangThaiDatTruoc trangThai) {
		this.phieuDatTruoc = phieuDatTruoc;
		this.tieuDe = tieuDe;
		this.trangThai = trangThai;
	}
	public void updateTrangThai(TrangThaiDatTruoc trangThaiDatTruoc,Dia dia){
		this.trangThai = trangThaiDatTruoc;
		this.dia=dia;
	}
}
