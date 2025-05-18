package com.ader.app;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;



@SpringBootApplication
@EnableDiscoveryClient  // Standardized annotation that works with Eureka
public class CountriesApplication {
  private static final Logger logger = LoggerFactory.getLogger(CountriesApplication.class);
  public static void main(String[] args) {
    logger.info("Starting Countries Service...");
    SpringApplication.run(CountriesApplication.class, args);
  }
}