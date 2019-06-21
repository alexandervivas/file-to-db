package com.ef.domain;

import java.util.Arrays;

public enum DurationEnum {

  HOURLY("hourly"),
  DAILY("daily"),
  UNKNOWN("");

  private String name;

  DurationEnum(String name) {
    this.name = name;
  }

  public static DurationEnum fromString(final String name) {
    return Arrays
        .asList(values())
        .stream()
        .filter(arg -> arg.name.equals(name))
        .findFirst()
        .orElse(UNKNOWN);
  }

}
