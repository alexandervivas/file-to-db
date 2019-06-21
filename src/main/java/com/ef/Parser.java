package com.ef;

import com.ef.db.repository.LogRepository;
import com.ef.db.repository.RiskRepository;
import com.ef.domain.Configuration;
import com.ef.domain.PotentialRisk;
import com.ef.task.DBWriter;
import com.ef.util.DateUtil;
import io.reactivex.Flowable;
import io.reactivex.flowables.ConnectableFlowable;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Parser {

  private static final Logger logger = LoggerFactory.getLogger(Parser.class);
  private static ExecutorService executorService = Executors.newCachedThreadPool();
  private static Configuration configuration = null;

  public static void main(
      String[] args) {// TODO too much logic here, needs refactor to place logic into controller

    logger.info("Reading arguments...");

    Optional<Configuration> optionalConfiguration = Configuration
        .fromCommandLineArguments(Arrays.asList(args));

    if (optionalConfiguration.isPresent()) {

      logger.info("Done");

      configuration = optionalConfiguration.get();

      performMigration();

      readSaveAndQuery();

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

  private static void readSaveAndQuery() {

    LogRepository logRepository = new LogRepository("mysql");// TODO read this from config file
    RiskRepository riskRepository = new RiskRepository("mysql");// TODO read this from config file
    List<Future> tasks = new ArrayList<>();

    Flowable<List<String>> flowable = Flowable
        .using(() -> new BufferedReader(new FileReader(configuration.getAccessLog())),
            reader -> Flowable.fromIterable(() -> reader.lines().iterator()), BufferedReader::close)
        .buffer(1000).doOnComplete(() -> {

          logger.info("Finished reading the file");

          for (Future<?> future : tasks) {
            future.get();
          }

          logger.info("All log entries saved in database");

          executorService.shutdown();

          logger.info("Getting potentially dangerous IP's");

          List<PotentialRisk> potentialRisks = logRepository
              .getPotentialRisk(configuration.getStartDate(),
                  DateUtil.getEndDate(configuration.getStartDate(), configuration.getDuration()),
                  configuration.getThreshold());

          potentialRisks.forEach(risk -> logger.info(risk.toString()));

          logger.info("Adding potential risk to the black list section on db");

          riskRepository.addAll(potentialRisks);

          logger.info("Done");

        });

    ConnectableFlowable<List<String>> cFlowable = flowable.publish();

    cFlowable
        .subscribe(list -> tasks.add(executorService.submit(new DBWriter(logRepository, list))));

    cFlowable.connect();


  }

}
