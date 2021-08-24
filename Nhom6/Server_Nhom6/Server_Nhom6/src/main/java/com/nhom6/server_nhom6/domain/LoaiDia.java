package com.nhom6.server_nhom6.domain;

import com.nhom6.server_nhom6.common.domain.AbstractEntity;
import com.nhom6.server_nhom6.status.TenLoaiDia;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "loaiDia")
@Getter
@NoArgsConstructor
public class LoaiDia extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long maLoaiDia;
	private TenLoaiDia tenLoaiDia;
	private double giaThue;
	private double phiTre;
	private int soNgayThue;

	private boolean active = true;

	@OneToMany(mappedBy = "loaiDia", fetch = FetchType.LAZY)
	private List<TieuDe> dsTuaDe;

	public void delete(){
		if(this.active ==false){
			throw new IllegalIdentifierException("Loai dia nay da bi xoa !");
		}
		this.active = false;
	}
	public void update(LoaiDia loaiDia){
		this.tenLoaiDia = loaiDia.tenLoaiDia;
	}

	//genarate code contructor getter ..... ALT+INSERT

	public LoaiDia( TenLoaiDia tenLoaiDia, double giaThue, double phiTre, int soNgayThue) {
		this.maLoaiDia = maLoaiDia;
		this.tenLoaiDia = tenLoaiDia;
		this.giaThue = giaThue;
		this.phiTre = phiTre;
		this.soNgayThue = soNgayThue;
	}

	public LoaiDia(long maLoaiDia, double giaThue, int soNgayThue) {
		this.maLoaiDia = maLoaiDia;
		this.giaThue = giaThue;
		this.soNgayThue = soNgayThue;
	}
}
