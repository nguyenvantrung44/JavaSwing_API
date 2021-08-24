package com.nhom6.server_nhom6;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;


@SpringBootApplication
@EnableJpaAuditing
public class ServerNhom6Application {
    public static void main(String[] args) {
        SpringApplication.run(ServerNhom6Application.class, args);
    }
}
