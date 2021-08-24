package dto.Post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostTieuDe {
    private long maTieuDe;
    private String tenTieuDe;
    private String tomTat;
    private Long loaiDia;

    public PostTieuDe(String tenTieuDe, String tomTat, Long loaiDia) {
        this.tenTieuDe = tenTieuDe;
        this.tomTat = tomTat;
        this.loaiDia = loaiDia;
    }
}
