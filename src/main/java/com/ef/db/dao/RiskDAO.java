package com.ef.db.dao;

import com.ef.db.manager.DBManager;
import com.ef.domain.PotentialRisk;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class RiskDAO {

  private DBManager manager;
  private Connection connection;

  private static final String INSERT_STATEMENT = "insert into blocked(ip, comments, date) VALUES(?,?,?)";

  public RiskDAO(DBManager manager, Connection connection) {
    this.manager = manager;
    this.connection = connection;
  }

  public void addAll(List<PotentialRisk> potentialRiskList) {

    PreparedStatement preparedStatement = null;

    try {

      preparedStatement = connection.prepareStatement(INSERT_STATEMENT);

      for (PotentialRisk potentialRisk : potentialRiskList) {
        preparedStatement.setString(1, potentialRisk.getIp());
        preparedStatement.setString(2, String
            .format("%s is being blocked by making %d requests in a short period of time",
                potentialRisk.getIp(), potentialRisk.getRequests()));
        preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

        preparedStatement.addBatch();

      }

      preparedStatement.executeBatch();

    } catch (SQLException e) {

      e.printStackTrace();// TODO use a logger here

    } finally {

      manager.closeResources(null, preparedStatement);

    }

  }


}
