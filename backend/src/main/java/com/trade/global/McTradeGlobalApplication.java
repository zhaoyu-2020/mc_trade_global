package com.trade.global;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.trade.global.mapper")
public class McTradeGlobalApplication {

    public static void main(String[] args) {
        SpringApplication.run(McTradeGlobalApplication.class, args);
    }
}
