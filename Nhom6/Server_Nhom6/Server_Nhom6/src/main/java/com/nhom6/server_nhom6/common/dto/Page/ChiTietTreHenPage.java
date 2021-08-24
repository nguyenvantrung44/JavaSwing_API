package com.nhom6.server_nhom6.common.dto.Page;

import com.nhom6.server_nhom6.common.dto.ChiTietPhiTreHenDto;
import com.nhom6.server_nhom6.common.dto.ChiTietPhieuDatTruocDto;
import com.nhom6.server_nhom6.common.page.PaginationMeta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietTreHenPage {


    private PaginationMeta paginationMeta;
    private List<ChiTietPhiTreHenDto> chiTietPhiTreHenDtos;
}
