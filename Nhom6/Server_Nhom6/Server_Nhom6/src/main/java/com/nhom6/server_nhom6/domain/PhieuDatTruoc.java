package com.nhom6.server_nhom6.domain;

import com.nhom6.server_nhom6.common.domain.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "PhieuDatTruoc")
@Getter
@NoArgsConstructor
public class PhieuDatTruoc extends AbstractEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long maPhieuDatTruoc;
	
	@ManyToOne
	@JoinColumn(name = "maKhachHang")
	private KhachHang khachHang;


	@OneToMany(mappedBy = "phieuDatTruoc", cascade = CascadeType.ALL)
	private List<ChiTietPhieuDatTruoc> dsChiTietPhieuDatTruoc = new ArrayList<>();

	private boolean active = true;

	public PhieuDatTruoc(KhachHang khachHang) {
		this.khachHang = khachHang;
	}


}
