package dto.Page;


import dto.PhiTreHenDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import page.PaginationMeta;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PhiTreHenPage {

    private PaginationMeta paginationMeta;
    private List<PhiTreHenDto> phiTreHenDtos;

}
