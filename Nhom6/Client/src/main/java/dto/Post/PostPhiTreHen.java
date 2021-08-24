package dto.Post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostPhiTreHen {

    private Long khachHang;
    private double tongPhiTreHen;
    private double tienDaTra;

}
