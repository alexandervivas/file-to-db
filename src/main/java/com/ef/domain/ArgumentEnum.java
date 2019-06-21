package com.ef.domain;

import java.util.Arrays;

public enum ArgumentEnum {

  START_DATE("--startDate"),
  DURATION("--duration"),
  THRESHOLD("--threshold"),
  ACCESSLOG("--accesslog"),
  UNKNOWN("");

  private String name;

  ArgumentEnum(String name) {
    this.name = name;
  }

  public static ArgumentEnum fromString(final String name) {
    return Arrays
        .asList(values())
        .stream()
        .filter(arg -> arg.name.equals(name))
        .findFirst()
        .orElse(UNKNOWN);
  }

}
