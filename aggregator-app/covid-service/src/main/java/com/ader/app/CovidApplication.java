package com.ader.app;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class CovidApplication {
      private static final Logger logger = LoggerFactory.getLogger(CovidApplication.class);
  public static void main(String[] args) {
    logger.info("Starting COVID Service...");
    SpringApplication.run(CovidApplication.class, args);
  }
}