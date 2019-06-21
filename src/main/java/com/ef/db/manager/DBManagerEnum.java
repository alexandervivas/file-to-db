package com.ef.db.manager;

import java.util.Arrays;

public enum DBManagerEnum {

  MYSQL("mysql"),
  H2("h2"),
  UNKNOWN("");

  private String name;

  DBManagerEnum(String name) {
    this.name = name;
  }

  public static DBManagerEnum fromString(String name) {

    return Arrays
        .asList(values())
        .stream()
        .filter(arg -> arg.name.equals(name))
        .findFirst()
        .orElse(UNKNOWN);

  }

}