package com.nhom6.server_nhom6.repository.Query.Impl;

import com.nhom6.server_nhom6.domain.ChiTietPhieuDatTruoc;
import com.nhom6.server_nhom6.domain.PhieuDatTruoc;
import com.nhom6.server_nhom6.domain.QChiTietPhieuDatTruoc;
import com.nhom6.server_nhom6.repository.Query.ChiTietPhieuDatTruocDsl;
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
public class ChiTietPhieuDatTruocdslImpl implements ChiTietPhieuDatTruocDsl {

    private final QChiTietPhieuDatTruoc qChiTietPhieuDatTruoc = QChiTietPhieuDatTruoc.chiTietPhieuDatTruoc;

    @PersistenceContext
    private EntityManager em;


    @Override
    public Page<ChiTietPhieuDatTruoc> getChiTietPDTId(long id, Pageable pageable) {

        JPAQuery<ChiTietPhieuDatTruoc> jpaQuery = new JPAQuery<>(em);
        JPAQuery<ChiTietPhieuDatTruoc> i = jpaQuery.from(qChiTietPhieuDatTruoc).where(qChiTietPhieuDatTruoc.chiTietPhieuDatTruocID.maPhieuDatTruoc.eq(id)).limit(pageable.getPageSize());
        return PageableExecutionUtils.getPage(i.fetch(),pageable,i::fetchCount);
    }
}
