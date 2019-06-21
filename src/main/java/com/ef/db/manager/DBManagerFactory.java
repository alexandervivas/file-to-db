package com.ef.db.manager;

import com.ef.db.manager.impl.H2DBManager;
import com.ef.db.manager.impl.MySQLDBManager;
import java.util.Optional;

public class DBManagerFactory {

  private static DBManagerFactory instance = null;

  private DBManagerFactory() {

  }

  public static DBManagerFactory getInstance() {

    if (instance == null) {

      synchronized (DBManagerFactory.class) {

        instance = new DBManagerFactory();

      }

    }

    return instance;

  }

  public Optional<DBManager> fromEnum(DBManagerEnum dbManagerEnum) {

    switch (dbManagerEnum) {

      case MYSQL:

        return Optional.of(new MySQLDBManager());

      case H2:

        return Optional.of(new H2DBManager());

      default:

        return Optional.empty();

    }

  }

}