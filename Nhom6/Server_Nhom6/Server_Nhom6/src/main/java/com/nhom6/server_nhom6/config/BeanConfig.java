package com.nhom6.server_nhom6.config;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class BeanConfig {
    @PersistenceContext
    private EntityManager em;

    @Bean
    public JPAQuery jpaQuery() {
        return new JPAQuery(em);
    }
}
