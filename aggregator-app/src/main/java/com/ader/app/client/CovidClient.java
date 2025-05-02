package com.ader.app.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.ader.app.dto.CovidStats;

@FeignClient(name = "covid-service")
public interface CovidClient {
  @GetMapping("/covid-management/countries/{name}")
  CovidStats getCovidStats(@PathVariable String name);
}