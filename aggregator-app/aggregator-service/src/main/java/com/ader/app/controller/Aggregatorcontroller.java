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

  @GetMapping("/countries-management/countries/{name}")
  public ResponseEntity<CombinedData> getCombinedData(@PathVariable("name") String name) {
    System.out.println("Received request for country: " + name);
    return ResponseEntity.ok(
      new CombinedData(
        countryClient.getCountry(name),
        covidClient.getCovidStats(name)
      )
    );
  }
}