package com.ader.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;



@SpringBootApplication
@EnableDiscoveryClient  // Standardized annotation that works with Eureka
public class CountriesApplication {
  public static void main(String[] args) {
    SpringApplication.run(CountriesApplication.class, args);
  }
}