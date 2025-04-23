package com.ader.app.dto;
import lombok.Data;

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
