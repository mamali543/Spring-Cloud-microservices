package com.ader.app.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.ader.app.dto.CountryData;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/countries-management")
public class CountryController {

  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLUE = "\u001B[34m";
  private final WebClient webClient;

  public CountryController() {
    this.webClient = WebClient.create("https://restcountries.com/v3.1");
  }

  @GetMapping("/countries/{name}")
  public ResponseEntity<CountryData> getCountry(@PathVariable("name") String name) {
    System.out.println(ANSI_BLUE + "Fetching country data for: " + name + ANSI_RESET);
    System.out.println(ANSI_BLUE + " ------------------------------------- " + ANSI_RESET);
    System.out.println(ANSI_BLUE + "Country name: " + name + ANSI_RESET);
    // Initiates a GET HTTP request To fetch data from restcountries.com
    // Expect an array of CountryData and take the first element
    CountryData data = webClient.get()
        .uri("/name/{name}?fields=name,capital,population", name)
        .retrieve()
        // Expect an array (CountryData[]) instead of a single object
        .bodyToMono(CountryData[].class)
        // Map the array to its first element
        .map(countries -> (countries != null && countries.length > 0) ? countries[0] : null)
        // Blocks (waits) for the asynchronous request to complete
        .block();

    // Handle case where no country data was found
    if (data == null) {
      return ResponseEntity.notFound().build();
    }
    System.out.println(ANSI_BLUE + "Country data fetched successfully for: " + name + ANSI_RESET);
    System.out.println(ANSI_BLUE + "-------------------------------------" + ANSI_RESET);
    System.out.println(ANSI_BLUE + data + ANSI_RESET); // Prints the toString() representation of CountryData
    System.out.println(ANSI_BLUE + "-------------------------------------" + ANSI_RESET);
    // Return the country data wrapped in a ResponseEntity
    // The ResponseEntity contains the HTTP status code and the body (country data)
    // The status code is 200 OK by default
    return ResponseEntity.ok(data);
  }
}