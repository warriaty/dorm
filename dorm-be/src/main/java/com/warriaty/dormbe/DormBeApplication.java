package com.warriaty.dormbe;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
public class DormBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DormBeApplication.class, args);
    }

}
