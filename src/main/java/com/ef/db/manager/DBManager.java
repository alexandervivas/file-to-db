package com.ef.db.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface DBManager {

  Connection getConnection();

  void returnConnection(Connection connection);

  void closeResources(ResultSet resultSet, PreparedStatement preparedStatement);

}