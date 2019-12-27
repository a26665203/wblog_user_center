package com.wblog.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@ImportResource({"classpath*:spring-config.xml"})
@SpringBootApplication
public class WebBlogApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder){
        return applicationBuilder.sources(WebBlogApplication.class);
    }
    public static void main(String[] args){
        SpringApplication.run(WebBlogApplication.class);
    }
}
