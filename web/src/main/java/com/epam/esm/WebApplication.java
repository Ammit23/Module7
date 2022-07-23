package com.epam.esm;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "com.epam.esm")
public class WebApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {

        new SpringApplicationBuilder(WebApplication.class)
               .profiles("prod")
               .run(args);
    }
}