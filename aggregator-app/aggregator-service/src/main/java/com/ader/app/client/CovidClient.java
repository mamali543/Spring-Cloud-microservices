package com.ader.app.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.ader.app.dto.CovidStats;

// HTTP Client developed by Netflix (now part of Spring Cloud) that simplifies calling RESTful services.
// Instead of manually writing REST API calls using RestTemplate or WebClient, Feign allows you to define an interface,
// and Spring dynamically generates the implementation at runtime.
@FeignClient(name = "covid-service")
public interface CovidClient {
  @GetMapping("/covid-management/countries/{name}")
  CovidStats getCovidStats(@PathVariable String name);
}