package com.nhom6.server_nhom6.repository;

import com.nhom6.server_nhom6.domain.PhieuDatTruoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuDatTruocRepository extends JpaRepository<PhieuDatTruoc,Long> {
}
