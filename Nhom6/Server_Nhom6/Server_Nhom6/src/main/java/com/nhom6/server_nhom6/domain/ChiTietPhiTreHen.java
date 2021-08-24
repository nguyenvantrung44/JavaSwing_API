package com.nhom6.server_nhom6.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "ChiTietPhiTreHen")
@Getter
@NoArgsConstructor
@Setter
public class ChiTietPhiTreHen{




	@EmbeddedId
	private ChiTietPhiTreHenID chiTietPhiTreHenID = new ChiTietPhiTreHenID();

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("maDia")
	@JoinColumn(name = "maDia")
	private Dia dia;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("maPhiTreHen")
	@JoinColumn(name = "maPhiTreHen")
	private PhiTreHen phiTreHen;
	
	@Column(name = "phiTre")
	private double phiTre;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "ngayTra")
	private LocalDate ngayTra;
	
	@Column(name = "soNgayTreHen")
	private int soNgayTreHen;

}
