package com.ader.app.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List; // Import List
import com.ader.app.dto.CountryData; // Import CountryData
import com.ader.app.dto.CovidStats; // Import CovidStats

@Data
@AllArgsConstructor
public class CombinedData {
  private CountryData country;
  private List<CovidStats> covidStats;
}