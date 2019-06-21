package com.ef.db.manager.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ef.db.manager.DBManager;
import org.h2.jdbcx.JdbcConnectionPool;

public class H2DBManager implements DBManager {

  private static JdbcConnectionPool pool;

  @Override
  public Connection getConnection() {

    pool = JdbcConnectionPool.create("jdbc:h2:mem:test;MODE=MySQL", "sa", "sa");

    Connection connection = null;

    try {

      connection = pool.getConnection();

    } catch (SQLException e) {

      e.printStackTrace();

    }

    return connection;
  }

  @Override
  public void returnConnection(Connection connection) {
    // Do nothing, this is just for testing

  }

  @Override
  public void closeResources(ResultSet resultSet, PreparedStatement preparedStatement) {
    try {

      if (resultSet != null) {
        resultSet.close();
      }

      if (preparedStatement != null) {
        preparedStatement.close();
      }

    } catch (SQLException e) {

      e.printStackTrace();

    }

  }

}