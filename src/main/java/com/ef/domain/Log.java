package com.ef.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {

  private LocalDateTime date;
  private String ip;
  private String request;
  private int status;
  private String userAgent;

  public Log(LocalDateTime date, String ip, String request, int status, String userAgent) {
    this.date = date;
    this.ip = ip;
    this.request = request;
    this.status = status;
    this.userAgent = userAgent;
  }

  public static Log fromString(String logStr) {

    String[] values = logStr.split("\\|");

    return new Log(LocalDateTime.parse(values[0], DateTimeFormatter
        .ofPattern("yyyy-MM-dd HH:mm:ss.SSS")), values[1], values[2].replace("\"", ""),
        Integer.parseInt(values[3]),
        values[4].replace("\"", ""));

  }

  public LocalDateTime getDate() {
    return date;
  }

  public String getIp() {
    return ip;
  }

  public String getRequest() {
    return request;
  }

  public int getStatus() {
    return status;
  }

  public String getUserAgent() {
    return userAgent;
  }

  @Override
  public String toString() {
    return "Log [date=" + date + ", ip=" + ip + ", request=" + request + ", status=" + status
        + ", userAgent=" + userAgent + "]";
  }

}