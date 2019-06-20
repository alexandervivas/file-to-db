package com.ef;

import com.ef.domain.Configuration;
import java.util.Arrays;
import java.util.Optional;
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

    } else {

      logger.error("There was an error reading the arguments");

    }
  }

}
