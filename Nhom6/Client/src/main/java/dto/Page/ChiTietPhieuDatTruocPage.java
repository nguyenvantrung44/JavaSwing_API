package dto.Page;
import dto.ChiTietPhieuDatTruocDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import page.PaginationMeta;

import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietPhieuDatTruocPage {

    private PaginationMeta paginationMeta;
    private List<ChiTietPhieuDatTruocDto> chiTietPhieuDatTruocDtos;
}
