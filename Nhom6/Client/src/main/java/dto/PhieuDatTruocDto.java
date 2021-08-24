package dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import service.ChiTietPhieuDatTruoc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
public class PhieuDatTruocDto {

	private long maPhieuDatTruoc;

	protected String ngayDat;

	private long makhachHang;

	private List<ChiTietPhieuDatTruoc> dsChiTietPhieuDatTruoc = new ArrayList<>();

	private boolean active;

	public PhieuDatTruocDto(long maPhieuDatTruoc, String ngayDat, long makhachHang, boolean active) {
		this.maPhieuDatTruoc = maPhieuDatTruoc;
		this.ngayDat = ngayDat;
		this.active = active;
		this.makhachHang = makhachHang;
	}
}
