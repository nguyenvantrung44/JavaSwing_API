package dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import status.TrangThaiPhiTreHen;

import java.util.ArrayList;
import java.util.List;



@Getter
@NoArgsConstructor
public class PhiTreHenDto {


	private long maPhiTreHen;

	private KhachHangDto khachHang;
	private double tongPhiTreHen;
	private double tienDaTra;

	private long maKhachHang;
	private TrangThaiPhiTreHen trangThai;


	private List<ChiTietPhiTreHenDto> dsChiTietPhiTreHen = new ArrayList<>();

	private boolean active ;

	public PhiTreHenDto( double tienDaTra, boolean active) {
		this.maPhiTreHen = maPhiTreHen;
		this.tienDaTra = tienDaTra;
		this.active = active;
	}

}
