package com.ader.app.controller;
import org.springframework.web.bind.annotation.*;
import org.apache.http.protocol.HTTP;
import org.springframework.http.ResponseEntity;
import com.ader.app.dto.CountryData;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/countries-management")
public class CountryController {
  private final WebClient webClient;

  public CountryController() {
    this.webClient = WebClient.create("https://restcountries.com/v3.1");
  }

  @GetMapping("/countries/{name}")
  public ResponseEntity<CountryData> getCountry(@PathVariable String name) {
                      // Initiates a GET HTTP request To fetch data from restcountries.com
    CountryData data = webClient.get()
      .uri("/name/{name}?fields=name,capital,population", name)
      .retrieve()
      //Converts the JSON response → Java object (CountryData)
      .bodyToMono(CountryData.class)
      //Blocks (waits) for the asynchronous request to complete, Converts reactive Mono<CountryData> → synchronous CountryData
      .block();
    return ResponseEntity.ok(data);
  }
}