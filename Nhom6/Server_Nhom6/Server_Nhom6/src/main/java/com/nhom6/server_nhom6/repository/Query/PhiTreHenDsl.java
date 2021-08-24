package com.nhom6.server_nhom6.repository.Query;


import com.nhom6.server_nhom6.domain.PhiTreHen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface PhiTreHenDsl {

    Page<PhiTreHen> getAllPhiTreHen(Pageable pageable);
    Page<PhiTreHen> getPhiTreHenByIdKH(@Param("id") long id, Pageable pageable);
}
