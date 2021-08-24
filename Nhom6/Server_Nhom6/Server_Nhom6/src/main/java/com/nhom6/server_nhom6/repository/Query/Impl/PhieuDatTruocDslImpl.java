package com.nhom6.server_nhom6.repository.Query.Impl;

import com.nhom6.server_nhom6.domain.PhiTreHen;
import com.nhom6.server_nhom6.domain.PhieuDatTruoc;
import com.nhom6.server_nhom6.domain.QPhieuDatTruoc;
import com.nhom6.server_nhom6.repository.Query.PhieuDatTruocDsl;
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
public class PhieuDatTruocDslImpl implements PhieuDatTruocDsl {

    private  final QPhieuDatTruoc qPhieuDatTruoc = QPhieuDatTruoc.phieuDatTruoc;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<PhieuDatTruoc> getAllPhieuDatTruoc(Pageable pageable) {
        JPAQuery<PhieuDatTruoc> jpaQuery =  new JPAQuery<>(em);
        JPAQuery<PhieuDatTruoc> phieuDatTruoc = jpaQuery.from(qPhieuDatTruoc).limit(pageable.getPageSize());

        return PageableExecutionUtils.getPage(phieuDatTruoc.fetch(),pageable,phieuDatTruoc::fetchCount);
    }


    @Override
    public Page<PhieuDatTruoc> getPhieuDatTruocByIdKH(long id, Pageable pageable) {
        JPAQuery<PhieuDatTruoc> jpaQuery = new JPAQuery<>(em);
        JPAQuery<PhieuDatTruoc> i = jpaQuery.from(qPhieuDatTruoc).where(qPhieuDatTruoc.khachHang.maKhachHang.eq(id)).limit(pageable.getPageSize());
        return PageableExecutionUtils.getPage(i.fetch(),pageable,i::fetchCount);
    }
}
