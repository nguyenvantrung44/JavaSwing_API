package dto.Page;

import dto.TieuDeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import page.PaginationMeta;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TieuDePage {
    private PaginationMeta paginationMeta;
    private List<TieuDeDto> tieuDeDtos;
}
