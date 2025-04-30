package com.ader.app.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.ader.app.dto.CovidStats;
import com.ader.app.dto.CovidApiResponse; // Keep the wrapper import
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List; // Import List
import java.util.Collections; // Import Collections for empty list

@RestController
@RequestMapping("/covid-management")
public class CovidController {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    private final WebClient webClient;

    public CovidController() {
        this.webClient = WebClient.create("https://covid-api.com/api");
    }

    @GetMapping("/countries/{name}")
    public ResponseEntity<List<CovidStats>> getCovidStats(@PathVariable("name") String name) {
        System.out.println(ANSI_BLUE + "Fetching covid data for: " + name + ANSI_RESET);
        System.out.println(ANSI_BLUE + " ------------------------------------- " + ANSI_RESET);
        System.out.println(ANSI_BLUE + "Country name: " + name + ANSI_RESET);

        List<CovidStats> statsList = webClient.get()
                .uri("/reports?iso={name}", name) // Using ISO code
                .retrieve()
                .bodyToMono(CovidApiResponse.class)
                .map(response -> (response != null) ? response.getData() : Collections.<CovidStats>emptyList())
                .block();

        if (statsList == null || statsList.isEmpty()) {
            System.err.println(
                    "No Covid stats data found for: " + name + ". API might have returned empty data array.");
            return ResponseEntity.ok(Collections.emptyList());
        }

        // Print each CovidStats object on a new line
        System.out.println(ANSI_BLUE + "Covid stats data fetched successfully for: " + name + ANSI_RESET);
        System.out.println(ANSI_BLUE + "-------------------------------------" + ANSI_RESET);
        for (CovidStats stats : statsList) {
            System.out.println(ANSI_BLUE + stats + ANSI_RESET); // Prints the toString() representation of each
                                                                // CovidStats
            System.out.println(ANSI_BLUE + "-------------------------------------" + ANSI_RESET);
        }

        return ResponseEntity.ok(statsList);
    }
}