package com.ef.task;

import com.ef.db.repository.LogRepository;
import com.ef.domain.Log;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBWriter implements Runnable {

  private static final Logger logger = LoggerFactory.getLogger(DBWriter.class);

  private LogRepository repository;
  private List<String> logs;

  public DBWriter(LogRepository repository, List<String> logs) {
    this.repository = repository;
    this.logs = logs;
  }

  @Override
  public void run() {

    logger.debug("Mapping logs...");

    List<Log> logs = this.logs
        .stream()
        .map(Log::fromString)
        .collect(Collectors.toList());

    logger.debug("Mapping logs... Done");

    logger.debug("Adding logs to database...");

    repository.addAll(logs);

    logger.debug("Adding logs to database... Done");

  }
}
