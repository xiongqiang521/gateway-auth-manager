package com.xq.gam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GatewayAuthManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayAuthManagerApplication.class, args);
    }

}
