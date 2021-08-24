package com.nhom6.server_nhom6.domain;

import com.nhom6.server_nhom6.common.domain.AbstractEntity;
import com.nhom6.server_nhom6.common.dto.Post.PostTieuDe;
import com.nhom6.server_nhom6.common.exception.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tieuDe")
@Getter
@NoArgsConstructor
public class TieuDe extends AbstractEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long maTieuDe;
	@Column(name = "tenTieuDe")
	private String tenTieuDe;
	@Column(name = "tomTat")
	private String tomTat;

	@Column(name = "maLoaiDia")
	private long maLoaiDia;

	@ManyToOne
	@JoinColumn(name = "maLoaiDia",insertable = false,updatable = false)
	private LoaiDia loaiDia;



	@OneToMany(mappedBy = "tieuDe", fetch = FetchType.LAZY)
	private List<Dia> dsDia;

	@OneToMany(mappedBy = "tieuDe", fetch = FetchType.LAZY)
	private List<ChiTietPhieuDatTruoc> dsChiTietPhieuDatTruoc = new ArrayList<>();

	private boolean active = true;

	public void deleteTieuDe() throws IllegalAccessError  {
		if(this.isActive()==false){
			throw new IllegalAccessError("tieu de is not exits");
		}
		this.active = false;
	}


	public TieuDe( String tenTieuDe, String tomTat, LoaiDia loaiDia) {
		this.tenTieuDe = tenTieuDe;
		this.tomTat = tomTat;
		this.loaiDia = loaiDia;
	}

	public TieuDe(String tenTieuDe, String tomTat, long maLoaiDia) {
		this.tenTieuDe = tenTieuDe;
		this.tomTat = tomTat;
		this.maLoaiDia = maLoaiDia;
	}

	public TieuDe(long maTieuDe) {
		this.maTieuDe = maTieuDe;
	}
}
