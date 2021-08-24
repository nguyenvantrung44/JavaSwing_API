package com.nhom6.server_nhom6.common.dto.Page;

import com.nhom6.server_nhom6.common.dto.DiaDto;
import com.nhom6.server_nhom6.common.page.PaginationMeta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiaPage {

        private PaginationMeta paginationMeta;
        private List<DiaDto> diaDtos;
}
