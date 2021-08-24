package com.nhom6.server_nhom6.repository.Query.Impl;

import com.nhom6.server_nhom6.domain.LoaiDia;
import com.nhom6.server_nhom6.domain.QLoaiDia;
import com.nhom6.server_nhom6.repository.Query.LoaiDiaDsl;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class LoaiDiaDslImpl implements LoaiDiaDsl {
    private final QLoaiDia qLoaiDia = QLoaiDia.loaiDia;
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<LoaiDia> getAll() {
        JPAQuery<LoaiDia> jpaQuery = new JPAQuery<>(em);
        return  jpaQuery.from(qLoaiDia).where(qLoaiDia.active.eq(true)).fetch();
    }
}
