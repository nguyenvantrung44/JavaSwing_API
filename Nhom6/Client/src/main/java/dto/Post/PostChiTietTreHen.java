package dto.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostChiTietTreHen {
    private long maDia;
    private long maTreHen;
    private String ngayTra;
    private double phiTre;
    private int soNgayTreHen;
}
