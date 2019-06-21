package com.ef.db.repository;

import com.ef.db.dao.RiskDAO;
import com.ef.db.manager.DBManager;
import com.ef.db.manager.DBManagerEnum;
import com.ef.db.manager.DBManagerFactory;
import com.ef.domain.PotentialRisk;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RiskRepository {

  private DBManager dbManager;

  public RiskRepository(String engine) {// TODO read this from config file

    dbManager = DBManagerFactory
        .getInstance()
        .fromEnum(DBManagerEnum.fromString(engine))
        .orElseThrow(() -> new RuntimeException(String
            .format("Could not find any suitable db manager to access '%s' database", engine)));

  }

  public void addAll(List<PotentialRisk> potentialRisks) {

    Connection connection = dbManager.getConnection();
    RiskDAO dao = new RiskDAO(dbManager, connection);

    try {

      dao.addAll(potentialRisks);
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
