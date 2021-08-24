package dto.Page;


import dto.DiaDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import page.PaginationMeta;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiaPage {

        private PaginationMeta paginationMeta;
        private List<DiaDto> diaDtos;
}
