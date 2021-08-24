package com.nhom6.server_nhom6.repository;

import com.nhom6.server_nhom6.domain.Dia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaRepository extends JpaRepository<Dia,Long> {



}
