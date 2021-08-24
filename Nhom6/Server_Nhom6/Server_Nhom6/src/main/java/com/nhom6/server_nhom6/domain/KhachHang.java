package com.nhom6.server_nhom6.domain;

import com.nhom6.server_nhom6.common.domain.AbstractEntity;
import com.nhom6.server_nhom6.common.dto.KhachHangDto;
import com.nhom6.server_nhom6.status.TrangThaiKhachHang;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.boot.model.naming.IllegalIdentifierException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "KhachHang")
@Getter
@NoArgsConstructor
public class KhachHang extends AbstractEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long maKhachHang;
	@Column(columnDefinition = "NVARCHAR(255)", nullable = false)
	private String hoTen;
	@Column(columnDefinition = "NVARCHAR(255)", nullable = false)
	private String diaChi;

	@Column(length = 10, nullable = false)
	private String soDienThoai;



	@OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL)
	private List<HoaDon> dsHoaDon = new ArrayList<>();
	@OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL)
	private List<PhieuDatTruoc> dsPhieuDatTruoc = new ArrayList<>();
	@OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL)
	private List<PhiTreHen> dsPhiTreHen = new ArrayList<>();
	
	private boolean trangThai = true;



	public void deleteKhachHang(){
		if(this.trangThai ==false){
			throw new IllegalIdentifierException("Khach Hang nay da bi xoa !");
		}
		this.trangThai = false;

	}

	public void updateKhachHang(KhachHangDto khachHangDto){
		this.hoTen = khachHangDto.getHoTen();
		this.soDienThoai = khachHangDto.getSoDienThoai();
		this.diaChi = khachHangDto.getDiaChi();
	}


	public KhachHang(String hoTen, String diaChi, String soDienThoai) {
		this.hoTen = hoTen;
		this.diaChi = diaChi;
		this.soDienThoai = soDienThoai;
	}



}
