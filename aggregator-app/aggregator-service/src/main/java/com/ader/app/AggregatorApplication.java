package com.ader.app;
import org.springframework.boot.SpringApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients  // Enables Feign clients
public class AggregatorApplication {

  private static final Logger logger = LoggerFactory.getLogger(AggregatorApplication.class);

  public static void main(String[] args) {
    logger.info("Starting Aggregator Service...");
    SpringApplication.run(AggregatorApplication.class, args);
  }
}