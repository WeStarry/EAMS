package com.example.eams;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.eams.mapper")
public class EamsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EamsApplication.class, args);
        System.out.println("系统启动中.......");
    }

}
