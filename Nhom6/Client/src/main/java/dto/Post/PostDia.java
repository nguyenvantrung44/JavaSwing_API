package dto.Post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import status.TrangThaiDia;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDia {

    private long maDia;
    private TrangThaiDia trangThai;

    private Long matieuDe;

    public PostDia(TrangThaiDia trangThai, Long matieuDe) {
        this.trangThai = trangThai;
        this.matieuDe = matieuDe;
    }
}
