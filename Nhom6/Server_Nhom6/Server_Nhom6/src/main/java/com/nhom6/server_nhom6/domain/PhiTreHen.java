package com.nhom6.server_nhom6.domain;

import com.nhom6.server_nhom6.common.domain.AbstractEntity;
import com.nhom6.server_nhom6.common.dto.PhiTreHenDto;
import com.nhom6.server_nhom6.status.TrangThaiPhiTreHen;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
public class PhiTreHen extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long maPhiTreHen;
	@ManyToOne
	@JoinColumn(name = "maKhachHang")
	private KhachHang khachHang;
	private double tongPhiTreHen;
	private double tienDaTra;


	@OneToMany(mappedBy = "phiTreHen", cascade = CascadeType.ALL)
	private List<ChiTietPhiTreHen> dsChiTietPhiTreHen = new ArrayList<>();

	private boolean active = true;

	public  void deletePhiTreHen() throws  IllegalAccessError{
		if(this.active == false){
			throw  new IllegalAccessError("PhiTreHen is not exits");
		}
		this.active = false;
	}


	public PhiTreHen(KhachHang khachHang, double tongPhiTreHen, double tienDaTra) {
		this.khachHang = khachHang;
		this.tongPhiTreHen = tongPhiTreHen;
		this.tienDaTra = tienDaTra;
	}

	public void updatePhiTreHen(PhiTreHenDto phiTreHenDto){
		this.tienDaTra = phiTreHenDto.getTienDaTra();
		this.active = phiTreHenDto.isActive();
	}

}
