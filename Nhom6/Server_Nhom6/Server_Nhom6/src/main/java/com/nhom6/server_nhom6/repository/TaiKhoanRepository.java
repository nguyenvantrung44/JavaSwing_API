package com.nhom6.server_nhom6.repository;

import com.nhom6.server_nhom6.domain.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaiKhoanRepository extends JpaRepository<TaiKhoan,Long> {

        public TaiKhoan findByEmail(String email);
}
