package com.community.api;

import org.broadleafcommerce.common.config.EnableBroadleafSiteRootAutoConfiguration;
import org.broadleafcommerce.common.web.boot.support.BroadleafBootServletContextInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

/**
 * @author Elbert Bautista (elbertbautista)
 */
@SpringBootApplication
@EnableAutoConfiguration
public class ApiApplication extends BroadleafBootServletContextInitializer {

    @Configuration
    @EnableBroadleafSiteRootAutoConfiguration
    public static class BroadleafFrameworkConfiguration {}

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
    
}