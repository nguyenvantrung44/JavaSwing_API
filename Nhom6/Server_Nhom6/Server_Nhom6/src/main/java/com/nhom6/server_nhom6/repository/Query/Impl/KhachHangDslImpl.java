package com.nhom6.server_nhom6.repository.Query.Impl;

import com.nhom6.server_nhom6.domain.KhachHang;
import com.nhom6.server_nhom6.domain.QKhachHang;
import com.nhom6.server_nhom6.repository.Query.KhachHangDsl;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class KhachHangDslImpl implements KhachHangDsl {

    private final QKhachHang qKhachHang = QKhachHang.khachHang;

    @PersistenceContext
    private EntityManager em;


    @Override
    public Page<KhachHang> getAllKhachHang(Pageable pageable) {
        JPAQuery<KhachHang> jpaQuery = new JPAQuery<>(em);
        JPAQuery<KhachHang> khachHang = jpaQuery.from(qKhachHang).limit(pageable.getPageSize());

        return PageableExecutionUtils.getPage(khachHang.fetch(),pageable,khachHang::fetchCount);
    }
}
