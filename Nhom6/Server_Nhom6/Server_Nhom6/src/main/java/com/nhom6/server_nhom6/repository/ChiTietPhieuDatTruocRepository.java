package com.nhom6.server_nhom6.repository;

import com.nhom6.server_nhom6.domain.ChiTietPhieuDatTruoc;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietPhieuDatTruocRepository extends JpaRepository<ChiTietPhieuDatTruoc,Long> {
//    @Query("SELECT * FROM chi_tiet_phieu_dat_truoc c where c.ma_phieu_dat_truoc =1 and c.ma_tua_de =1;")
//    ChiTietPhieuDatTruoc  atTruocByPhieuDatTruocAndTieuDe(@Param("idPhieuDatTruoc") Long idPhieuDatTruoc,@Param("idTieuDe") Long idTieuDe);
}
