package dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
public class HoaDonDto {


	private long maHoaDon;

	private String ngayThue;

	private String ngayPhaiTra;

	private double tongTien;

	private long makhachHang;

	private List<ChiTietHoaDonDto> dsChiTietHoaHon = new ArrayList<>();

}
