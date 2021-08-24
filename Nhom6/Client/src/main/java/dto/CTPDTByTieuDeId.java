package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CTPDTByTieuDeId {
    private Long maTieuDe;
    private String tenTieuDe;
    private Long maKhachHang;
    private Long maPhieuDatTruoc;
    private String ngayDat;
    private String trangThaiDatTruoc;
    private String maDia;
}
