package com.nhom6.server_nhom6.repository.Query.Impl;

import com.nhom6.server_nhom6.domain.ChiTietPhiTreHen;
import com.nhom6.server_nhom6.domain.PhiTreHen;
import com.nhom6.server_nhom6.domain.QChiTietPhiTreHen;
import com.nhom6.server_nhom6.domain.QPhiTreHen;
import com.nhom6.server_nhom6.repository.Query.PhiTreHenDsl;
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
public class PhiTreHenImpl implements PhiTreHenDsl {

    private  final QPhiTreHen qPhiTreHen = QPhiTreHen.phiTreHen;

    @PersistenceContext
    private EntityManager em;


    @Override
    public Page<PhiTreHen> getAllPhiTreHen(Pageable pageable) {
        JPAQuery<PhiTreHen> jpaQuery =  new JPAQuery<>(em);
        JPAQuery<PhiTreHen> phiTreHen = jpaQuery.from(qPhiTreHen).limit(pageable.getPageSize());

        return PageableExecutionUtils.getPage(phiTreHen.fetch(),pageable,phiTreHen::fetchCount);
    }

    @Override
    public Page<PhiTreHen> getPhiTreHenByIdKH(long id, Pageable pageable) {
        JPAQuery<PhiTreHen> jpaQuery = new JPAQuery<>(em);
        JPAQuery<PhiTreHen> i = jpaQuery.from(qPhiTreHen).where(qPhiTreHen.khachHang.maKhachHang.eq(id)).limit(pageable.getPageSize());
        return PageableExecutionUtils.getPage(i.fetch(),pageable,i::fetchCount);
    }


}
