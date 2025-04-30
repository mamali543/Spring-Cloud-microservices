package com.ader.app.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // Ignore fields not explicitly mapped
public class CovidStats {

    private String date;
    private long confirmed;
    private long deaths;
    private long recovered;

    @JsonProperty("confirmed_diff") // Map JSON key to Java field name
    private long confirmedDiff;

    @JsonProperty("deaths_diff")
    private long deathsDiff;

    @JsonProperty("recovered_diff")
    private long recoveredDiff;

    @JsonProperty("last_update")
    private String lastUpdate;

    private long active;

    @JsonProperty("active_diff")
    private long activeDiff;

    @JsonProperty("fatality_rate")
    private double fatalityRate;

    // Nested region object
    private RegionStats region;

    @Data
    public class RegionStats {
        private String iso;
        private String name;
        private String province;
        private String lat;
        @JsonProperty("long") // Map JSON "long" to Java field "longitude"
        private String longitude;
        private List<String> cities;
    }
}