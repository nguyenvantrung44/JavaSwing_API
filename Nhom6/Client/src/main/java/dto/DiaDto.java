package dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import status.TrangThaiDia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Setter
public class DiaDto {


    private long maDia;
    private String trangThai;


    private  String tenTieuDe;

    private List<ChiTietHoaDonDto> dsChiTietHoaHon;

//	private List<ChiTietPhiTreHen> dsChiTietPhiTreHen = new ArrayList<>();

    private List<HoaDonDto> hoaDonDtos;


    private String ngayThue;
    private String ngayPhaiTra;
    private Long maKhachHang;
    private Long maTieuDe;

    public DiaDto(long maDia, String tenTieuDe, String ngayThue, String ngayPhaiTra, Long maKhachHang,Long maTieuDe) {
        this.maDia = maDia;
        this.tenTieuDe = tenTieuDe;
        this.ngayThue = ngayThue;
        this.ngayPhaiTra = ngayPhaiTra;
        this.maKhachHang = maKhachHang;
        this.maTieuDe=maTieuDe;
    }
    public DiaDto(long maDia, String tenTieuDe,Long maTieuDe) {
        this.maDia = maDia;
        this.tenTieuDe = tenTieuDe;
        this.ngayThue = ngayThue;
        this.ngayPhaiTra = ngayPhaiTra;
        this.maKhachHang = maKhachHang;
        this.maTieuDe=maTieuDe;
    }
    public DiaDto(long maDia, String trangThai, String tenTieuDe) {
        this.maDia = maDia;
        this.trangThai = trangThai;
        this.tenTieuDe = tenTieuDe;
    }

    public DiaDto(long maDia, String tenTieuDe, List<HoaDonDto> hoaDonDtos) {
        this.maDia = maDia;
        this.tenTieuDe = tenTieuDe;
        this.hoaDonDtos = hoaDonDtos;
    }



}
