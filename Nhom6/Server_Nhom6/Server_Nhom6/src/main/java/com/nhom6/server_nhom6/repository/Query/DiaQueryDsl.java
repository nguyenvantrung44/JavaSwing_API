package com.nhom6.server_nhom6.repository.Query;

import com.nhom6.server_nhom6.domain.Dia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiaQueryDsl {

    Page<Dia> getAllDia(Pageable pageable);
}
