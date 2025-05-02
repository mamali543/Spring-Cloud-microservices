package com.ader.app.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.ader.app.dto.CombinedData;
import com.ader.app.client.CountryClient;
import com.ader.app.client.CovidClient;

@RestController
@RequestMapping("/information-management")
public class AggregatorController {
  private final CountryClient countryClient;
  private final CovidClient covidClient;

  public AggregatorController(CountryClient countryClient, CovidClient covidClient) {
    this.countryClient = countryClient;
    this.covidClient = covidClient;
  }

  @GetMapping("/countries/{name}")
  public ResponseEntity<CombinedData> getCombinedData(@PathVariable String name) {
    return ResponseEntity.ok(
      new CombinedData(
        countryClient.getCountry(name),
        covidClient.getCovidStats(name)
      )
    );
  }
}