package com.ader.app.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.ader.app.dto.CountryData;

@FeignClient(name = "countries-service")
public interface CountryClient {
  @GetMapping("/countries-management/countries/{name}")
  CountryData getCountry(@PathVariable String name);
}
