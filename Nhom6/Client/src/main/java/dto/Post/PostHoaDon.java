package dto.Post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostHoaDon {


    private String ngayThue;

    private String ngayPhaiTra;

    private double tongTien;

    private long khachHang;

}
