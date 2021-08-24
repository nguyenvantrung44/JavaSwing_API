package com.nhom6.server_nhom6.common.dto.Post;


import com.nhom6.server_nhom6.status.TrangThaiDia;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDia {

    private long maDia;
    private TrangThaiDia trangThai;

    private Long matieuDe;

}
