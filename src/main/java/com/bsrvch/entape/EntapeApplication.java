package com.bsrvch.entape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EntapeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EntapeApplication.class, args);
    }

    @Autowired
    public void configureGlobal(){

    }
}
