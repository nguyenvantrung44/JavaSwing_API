package com.nhom6.server_nhom6.repository.Query.Impl;

import com.nhom6.server_nhom6.domain.ChiTietPhiTreHen;
import com.nhom6.server_nhom6.domain.ChiTietPhieuDatTruoc;
import com.nhom6.server_nhom6.domain.QChiTietPhiTreHen;
import com.nhom6.server_nhom6.repository.Query.ChiTietPhiTreHenDsl;
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
public class ChiTietPhiTreHenDslImpl implements ChiTietPhiTreHenDsl {

    private final QChiTietPhiTreHen qChiTietPhiTreHen = QChiTietPhiTreHen.chiTietPhiTreHen;

    @PersistenceContext
    private EntityManager em;


    @Override
    public Page<ChiTietPhiTreHen> getChiTietPTId(long id, Pageable pageable) {

        JPAQuery<ChiTietPhiTreHen> jpaQuery = new JPAQuery<>(em);
        JPAQuery<ChiTietPhiTreHen> i = jpaQuery.from(qChiTietPhiTreHen).where(qChiTietPhiTreHen.chiTietPhiTreHenID.maPhiTreHen.eq(id)).limit(pageable.getPageSize());
        return PageableExecutionUtils.getPage(i.fetch(),pageable,i::fetchCount);
    }
}
