package com.moma.momaadmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.moma.momaadmin.mapper")
public class MomaAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MomaAdminApplication.class, args);
    }

}
