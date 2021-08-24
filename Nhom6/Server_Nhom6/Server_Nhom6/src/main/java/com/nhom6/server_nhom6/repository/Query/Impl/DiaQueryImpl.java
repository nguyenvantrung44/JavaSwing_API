package com.nhom6.server_nhom6.repository.Query.Impl;

import com.nhom6.server_nhom6.domain.Dia;
import com.nhom6.server_nhom6.domain.QDia;
import com.nhom6.server_nhom6.repository.Query.DiaQueryDsl;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class DiaQueryImpl implements DiaQueryDsl {
    private final QDia qDia = QDia.dia;
    @PersistenceContext
    private EntityManager em;
    @Override
    public Page<Dia> getAllDia(Pageable pageable) {
        JPAQuery<Dia> jpaQuery = new JPAQuery<>(em);
        JPAQuery<Dia> dia = jpaQuery.from(qDia).limit(pageable.getPageSize());
        return PageableExecutionUtils.getPage(dia.fetch(),pageable,dia::fetchCount);
    }
}
