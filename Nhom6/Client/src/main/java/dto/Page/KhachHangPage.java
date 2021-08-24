package dto.Page;

import dto.KhachHangDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import page.PaginationMeta;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KhachHangPage {

        private PaginationMeta paginationMeta;
        private List<KhachHangDto> khachHangDtos;
}
