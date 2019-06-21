package com.ef.db.manager.impl;

import com.ef.db.manager.DBManager;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLDBManager implements DBManager {

  private ComboPooledDataSource pool;

  public MySQLDBManager() {// TODO read this from a config file

    try {

      pool = new ComboPooledDataSource();
      pool.setDriverClass("com.mysql.cj.jdbc.Driver");
      pool.setJdbcUrl("jdbc:mysql://localhost:3306/wallethub");
      pool.setUser("root");
      pool.setPassword("wallethub");

    } catch (PropertyVetoException e) {

      e.printStackTrace();// TODO use a logger here

    }

  }

  @Override
  public Connection getConnection() {

    Connection connection = null;

    try {

      connection = pool.getConnection();
      connection.setAutoCommit(false);

    } catch (SQLException e) {

      e.printStackTrace();// TODO use a logger here

    }

    return connection;

  }

  @Override
  public void returnConnection(Connection connection) {

    try {

      if (connection != null) {

        connection.close();

      }

    } catch (SQLException e) {

      e.printStackTrace();// TODO use a logger here

    }

  }

  @Override
  public void closeResources(ResultSet resultSet, PreparedStatement preparedStatement) {

    try {

      if (resultSet != null) {

        resultSet.close();

      }

    } catch (SQLException e) {

      e.printStackTrace();// TODO use a logger here

    }

    try {

      if (preparedStatement != null) {

        preparedStatement.close();

      }

    } catch (SQLException e) {

      e.printStackTrace();// TODO use a logger here

    }

  }

}