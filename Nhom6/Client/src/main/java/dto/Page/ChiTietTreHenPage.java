package dto.Page;


import dto.ChiTietPhiTreHenDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import page.PaginationMeta;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietTreHenPage {


    private PaginationMeta paginationMeta;
    private List<ChiTietPhiTreHenDto> chiTietPhiTreHenDtos;
}
