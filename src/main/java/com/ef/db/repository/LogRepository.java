package com.ef.db.repository;

import com.ef.db.dao.LogDAO;
import com.ef.db.manager.DBManager;
import com.ef.db.manager.DBManagerEnum;
import com.ef.db.manager.DBManagerFactory;
import com.ef.domain.Log;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class LogRepository {

  private DBManager dbManager;

  public LogRepository(String engine) {// TODO read this from config file

    dbManager = DBManagerFactory
        .getInstance()
        .fromEnum(DBManagerEnum.fromString(engine))
        .orElseThrow(() -> new RuntimeException(String
            .format("Could not find any suitable db manager to access '%s' database", engine)));

  }

  public void addAll(List<Log> logs) {

    Connection connection = dbManager.getConnection();

    LogDAO dao = new LogDAO(dbManager, connection);

    try {

      dao.addAll(logs);
      connection.commit();

    } catch (SQLException e) {

      try {

        connection.rollback();

      } catch (SQLException ex) {

        ex.printStackTrace();// TODO use a logger here

      }

    } finally {

      dbManager.returnConnection(connection);

    }

  }

}
