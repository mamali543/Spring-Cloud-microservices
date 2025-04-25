package com.ader.app.dto;
import lombok.Data;

//This class mirrors the JSON structure returned by restcountries.com
@Data
public class CountryData {
  private Name name;
  private String[] capital;
  private Long population;

  @Data
  public static class Name {
    private String common;
    private String official;
  }
}
