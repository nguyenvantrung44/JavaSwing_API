package com.nhom6.server_nhom6.common.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class AbstractEntity {


    @Column(name = "createdDate")
    @CreatedDate
    protected Timestamp createdDate;


    @Column(name = "updatedDate")
    @LastModifiedDate
    protected Timestamp updatedDate;



}