package dto.Post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import status.TenLoaiDia;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLoaiDia {
    private TenLoaiDia tenLoaiDia;
    private double giaThue;
    private double phiTre;
    private int soNgayThue;
}
