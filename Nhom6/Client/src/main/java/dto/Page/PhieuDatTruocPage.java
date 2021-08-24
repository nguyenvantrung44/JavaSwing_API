package dto.Page;


import dto.PhieuDatTruocDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import page.PaginationMeta;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PhieuDatTruocPage {


    private PaginationMeta paginationMeta;
    private List<PhieuDatTruocDto> phieuDatTruocDtos;
}
