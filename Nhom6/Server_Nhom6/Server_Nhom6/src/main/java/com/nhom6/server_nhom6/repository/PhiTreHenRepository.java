package com.nhom6.server_nhom6.repository;

import com.nhom6.server_nhom6.domain.PhiTreHen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhiTreHenRepository extends JpaRepository<PhiTreHen,Long> {
}
