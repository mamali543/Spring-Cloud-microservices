package com.ader.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient  // Register as Eureka client
public class ApiGatewayApplication { 
  private static final Logger logger = LoggerFactory.getLogger(ApiGatewayApplication.class);
  public static void main(String[] args) {
    logger.info("Starting API Gateway...");
    SpringApplication.run(ApiGatewayApplication.class, args);
  }
}