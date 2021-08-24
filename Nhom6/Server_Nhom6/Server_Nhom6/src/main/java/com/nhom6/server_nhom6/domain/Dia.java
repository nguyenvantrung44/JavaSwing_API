package com.nhom6.server_nhom6.domain;

import com.nhom6.server_nhom6.common.domain.AbstractEntity;
import com.nhom6.server_nhom6.status.TrangThaiDia;
import lombok.*;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dia")
@ToString(of = {"maDia","trangThai","maTieuDe",})
public class Dia extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long maDia;

	@Column(name = "trangthai")
	private TrangThaiDia trangThai;

	// Lấy mã tiêu đề ra từ getAllDia
	@Column(name = "maTieuDe")
	private  long maTieuDe;

	@ManyToOne
	@JoinColumn(name = "maTieuDe", insertable = false,updatable = false)
	private TieuDe tieuDe;

	@OneToMany(mappedBy = "dia")
	private List<ChiTietHoaDon> dsChiTietHoaHon = new ArrayList<>();


	@OneToMany(mappedBy = "dia", fetch = FetchType.LAZY)
	private List<ChiTietPhiTreHen> dsChiTietPhiTreHen = new ArrayList<>();


	@OneToMany(mappedBy="dia",fetch = FetchType.LAZY)
	private List<ChiTietPhieuDatTruoc> chiTietPhieuDatTruocs;

	public  void deleteDia() throws  IllegalAccessError{
		if(this.trangThai == TrangThaiDia.ngungChoThue){
			throw  new IllegalAccessError("Dia is not exits");
		}

		this.trangThai = TrangThaiDia.ngungChoThue;
	}

	public  void daThueDia() throws  IllegalAccessError{
		if(this.trangThai == TrangThaiDia.daThue){
			throw  new IllegalAccessError("Dia DA THUE");
		}

		this.trangThai = TrangThaiDia.daThue;
	}

	public void daTruocDia() throws  IllegalAccessError{
		if(this.trangThai == TrangThaiDia.daGan){
			throw  new IllegalAccessError("Dia DA DUOC DAT TRUOC");
		}

		this.trangThai = TrangThaiDia.daGan;
	}

	public Dia(@Nullable long maDia, TrangThaiDia trangThai, TieuDe tieuDe) {
		this.maDia = maDia;
		this.trangThai = trangThai;
		this.tieuDe = tieuDe;
	}

	public Dia(TrangThaiDia trangThai, TieuDe tieuDe) {
		this.trangThai = trangThai;
		this.tieuDe = tieuDe;
	}

	public Dia(TrangThaiDia trangThai, long maTieuDe) {
		this.trangThai = trangThai;
		this.maTieuDe = maTieuDe;
	}

	public Dia(long maDia, TrangThaiDia trangThai, long maTieuDe) {
		this.maDia = maDia;
		this.trangThai = trangThai;
		this.maTieuDe = maTieuDe;
	}

	public Dia(long maTieuDe) {
		this.maTieuDe = maTieuDe;
	}


	public void updateTrangThaiDia(TrangThaiDia trangThai) {
		this.trangThai = trangThai;
	}
}
