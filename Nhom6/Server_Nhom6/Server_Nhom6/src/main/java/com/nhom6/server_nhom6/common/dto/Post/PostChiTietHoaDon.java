package com.nhom6.server_nhom6.common.dto.Post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostChiTietHoaDon {

        private long maDia;
        private long maHoaDon;


}
