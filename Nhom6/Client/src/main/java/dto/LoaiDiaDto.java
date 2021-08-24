package dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import status.TenLoaiDia;

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



}
