package com.ader.app;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients  // Enables Feign clients
public class AggregatorApplication {
  public static void main(String[] args) {
    SpringApplication.run(AggregatorApplication.class, args);
  }
}