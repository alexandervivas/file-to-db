package com.ef.domain;

public class PotentialRisk {

  private String ip;
  private Long requests;

  public PotentialRisk(String ip, Long requests) {
    this.ip = ip;
    this.requests = requests;
  }

  public String getIp() {
    return ip;
  }

  public Long getRequests() {
    return requests;
  }

  @Override
  public String toString() {
    return "PotentialRisk{" +
        "ip='" + ip + '\'' +
        ", requests=" + requests +
        '}';
  }

}
