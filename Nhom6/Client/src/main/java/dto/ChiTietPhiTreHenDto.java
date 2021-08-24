package dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietPhiTreHenDto {
    private long maDia;


    private long maphiTreHen;

    private double phiTre;

    private String ngayTra;


    private int soNgayTreHen;
}
