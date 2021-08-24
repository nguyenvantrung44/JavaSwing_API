package com.nhom6.server_nhom6.repository;

import com.nhom6.server_nhom6.domain.LoaiDia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoaiDiaRepository extends JpaRepository<LoaiDia,Long> {
}
