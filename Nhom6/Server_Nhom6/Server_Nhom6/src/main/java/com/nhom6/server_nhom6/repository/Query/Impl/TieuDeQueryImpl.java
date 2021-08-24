package com.nhom6.server_nhom6.repository.Query.Impl;

import com.nhom6.server_nhom6.domain.Dia;
import com.nhom6.server_nhom6.domain.QTieuDe;
import com.nhom6.server_nhom6.domain.TieuDe;
import com.nhom6.server_nhom6.repository.Query.TieuDeQueryDsl;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.querydsl.core.QueryModifiers.limit;

@Repository
@RequiredArgsConstructor
public class TieuDeQueryImpl implements TieuDeQueryDsl {
    private final QTieuDe qTieuDe = QTieuDe.tieuDe;
    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<TieuDe> getAll(Pageable pageable) {
        JPAQuery<TieuDe> jpaQuery = new JPAQuery<>(em);
        JPAQuery<TieuDe> j =jpaQuery.from(qTieuDe).limit(pageable.getPageSize());
        return PageableExecutionUtils.getPage(j.fetch(),pageable,j::fetchCount);
    }

    @Override
    public Page<TieuDe> getIDByName(String tenTieude, Pageable pageable) {
        JPAQuery<TieuDe> jpaQuery = new JPAQuery<>(em);
        JPAQuery<TieuDe> i =jpaQuery.from(qTieuDe).where(qTieuDe.tenTieuDe.eq(tenTieude)).limit(pageable.getPageSize());
        return PageableExecutionUtils.getPage(i.fetch(),pageable,i::fetchCount);
    }
}
