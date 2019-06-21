package com.ef;

import com.ef.db.repository.LogRepository;
import com.ef.domain.Configuration;
import com.ef.domain.Log;
import com.ef.task.DBWriter;
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
import java.util.stream.Collectors;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Parser {

  private static final Logger logger = LoggerFactory.getLogger(Parser.class);

  private static ExecutorService executorService = Executors.newCachedThreadPool();
  ;

  public static void main(String[] args) {

    logger.info("Reading arguments...");

    Optional<Configuration> optionalConfiguration = Configuration
        .fromCommandLineArguments(Arrays.asList(args));

    if (optionalConfiguration.isPresent()) {

      logger.info("Done");

      Configuration configuration = optionalConfiguration.get();

      performMigration();

      readAndSave(configuration.getAccessLog());

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

  private static void readAndSave(String filename) {
    LogRepository repository = new LogRepository("mysql");// TODO read this from config file
    List<Future> tasks = new ArrayList<>();
    // read with backpressure
    Flowable<List<String>> flowable = Flowable.using(
        () -> new BufferedReader(new FileReader(filename)),
        reader -> Flowable.fromIterable(() -> reader.lines().iterator()),
        BufferedReader::close)
        .buffer(1000)
        .doOnComplete(() -> {
          logger.info("Finished emiting..............................................");
          for (Future<?> future : tasks) {
            future.get();
          }
          logger.info("THAT'S IT!");
          executorService.shutdown();
        });

    ConnectableFlowable<List<String>> cFlowable = flowable.publish();

    cFlowable.subscribe(list -> tasks.add(executorService.submit(new DBWriter(repository, list))));

    cFlowable.connect();


  }

}
