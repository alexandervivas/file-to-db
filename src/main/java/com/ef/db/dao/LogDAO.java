package com.ef.db.dao;

import com.ef.domain.Log;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.ef.db.manager.DBManager;

public class LogDAO {

  private DBManager manager;
  private Connection connection;

  private static final String INSERT_STATEMENT = "insert ignore into logs(date, ip, request, status, user_agent) VALUES(?,?,?,?,?)";

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

      e.printStackTrace();

    } finally {

      manager.closeResources(null, preparedStatement);

    }

  }

}
