package com.ader.app.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CombinedData {
  private CountryData country;
  private CovidStats covidStats;
}