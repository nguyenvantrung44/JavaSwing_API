package com.nhom6.server_nhom6.repository;

import com.nhom6.server_nhom6.domain.TieuDe;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface TieuDeRepository extends JpaRepository<TieuDe,Long> {

//        @Query("SELECT t.maTieuDe FROM TieuDe t WHERE t.tenTieuDe = :tenTieuDe")
//        TieuDe findMaByTenTieuDe(@Param("tenTieuDe") String tenTieuDe);


}
