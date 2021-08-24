package  dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;



@Getter
@NoArgsConstructor
public class KhachHangDto {

	private long maKhachHang;

	private String hoTen;

	private String diaChi;

	private String soDienThoai;

	private List<HoaDonDto> dsHoaDon = new ArrayList<>();

	private List<PhieuDatTruocDto> dsPhieuDatTruoc = new ArrayList<>();

	private List<PhiTreHenDto> dsPhiTreHen = new ArrayList<>();
	
	private boolean trangThai = true;

	public KhachHangDto(long maKhachHang, String hoTen, String diaChi, String soDienThoai, boolean trangThai) {
		this.maKhachHang = maKhachHang;
		this.hoTen = hoTen;
		this.diaChi = diaChi;
		this.soDienThoai = soDienThoai;
		this.trangThai = trangThai;
	}
}
