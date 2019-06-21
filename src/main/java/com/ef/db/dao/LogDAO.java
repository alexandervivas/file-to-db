package com.ef.db.dao;

import com.ef.domain.Log;
import com.ef.domain.PotentialRisk;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ef.db.manager.DBManager;

public class LogDAO {

  private DBManager manager;
  private Connection connection;

  private static final String INSERT_STATEMENT = "insert ignore into logs(date, ip, request, status, user_agent) VALUES(?,?,?,?,?)";
  private static final String SELECT_STATEMENT = "select ip, count(1) from logs where date between ? and ? group by ip having count(1) > ?";

  public LogDAO(DBManager manager, Connection connection) {
    this.manager = manager;
    this.connection = connection;
  }

  public void addAll(List<Log> logs) {

    PreparedStatement preparedStatement = null;

    try {

      preparedStatement = connection.prepareStatement(INSERT_STATEMENT);

      for (Log log : logs) {
        preparedStatement.setTimestamp(1, Timestamp.valueOf(log.getDate()));
        preparedStatement.setString(2, log.getIp());
        preparedStatement.setString(3, log.getRequest());
        preparedStatement.setInt(4, log.getStatus());
        preparedStatement.setString(5, log.getUserAgent());

        preparedStatement.addBatch();
      }

      preparedStatement.executeBatch();

    } catch (SQLException e) {

      e.printStackTrace();// TODO use a logger here

    } finally {

      manager.closeResources(null, preparedStatement);

    }

  }

  public List<PotentialRisk> getPotentialRisk(LocalDateTime startDate, LocalDateTime endDate,
      int threshold) {

    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    List<PotentialRisk> result = new ArrayList<>();

    try {

      preparedStatement = connection.prepareStatement(SELECT_STATEMENT);
      preparedStatement.setTimestamp(1, Timestamp.valueOf(startDate));
      preparedStatement.setTimestamp(2, Timestamp.valueOf(endDate));
      preparedStatement.setInt(3, threshold);

      resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        result.add(new PotentialRisk(resultSet.getString(1), resultSet.getLong(2)));
      }

    } catch (SQLException e) {

      e.printStackTrace();// TODO use a logger here

    } finally {

      manager.closeResources(resultSet, preparedStatement);

    }

    return result;

  }

}
