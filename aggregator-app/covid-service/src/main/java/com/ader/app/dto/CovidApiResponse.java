package com.ader.app.dto;

import lombok.Data;
import java.util.List; // Import List

@Data
public class CovidApiResponse {
    // Expect a List of CovidStats objects under the "data" key
    private List<CovidStats> data;
}
