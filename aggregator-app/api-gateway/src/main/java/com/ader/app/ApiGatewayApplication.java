package com.ader.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient  // Register as Eureka client
public class ApiGatewayApplication { 
  public static void main(String[] args) {
    SpringApplication.run(ApiGatewayApplication.class, args);
  }
}