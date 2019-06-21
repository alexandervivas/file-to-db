package com.ef;

import com.ef.domain.Configuration;
import java.util.Arrays;
import java.util.Optional;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Parser {

  private static final Logger logger = LoggerFactory.getLogger(Parser.class);

  public static void main(String[] args) {

    logger.info("Reading arguments...");

    Optional<Configuration> configuration = Configuration
        .fromCommandLineArguments(Arrays.asList(args));

    if (configuration.isPresent()) {

      logger.info("Done");

      performMigration();

    } else {

      logger.error("There was an error reading the arguments");

    }
  }

  private static void performMigration() {

    logger.info("Performing database migration");

    Flyway flyway = Flyway.configure()
        .dataSource("jdbc:mysql://localhost:3306/wallethub", "root", "wallethub")
        .load();// TODO read this from config file
    flyway.migrate();

    logger.info("Done");

  }

}
