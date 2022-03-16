package com.myiot.myserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@MapperScan("com.myiot.myserver.mapper")
@SpringBootApplication
public class MyServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyServerApplication.class, args);
    }

}
