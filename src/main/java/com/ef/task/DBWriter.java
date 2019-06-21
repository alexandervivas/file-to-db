package com.ef.task;

import com.ef.db.manager.DBManager;
import com.ef.db.repository.LogRepository;
import com.ef.domain.Log;
import java.util.List;
import java.util.stream.Collectors;

public class DBWriter /*implements Runnable*/ {

  /*private LogRepository repository;
  private List<String> logs;

  public DBWriter(LogRepository repository, List<String> logs) {
    this.repository = repository;
    this.logs = logs;
  }

  @Override
  public void run() {

    List<Log> logs = this.logs
        .stream()
        .map(Log::fromString)
        .collect(Collectors.toList());

    repository.addAll(logs);

  }*/

  public static void temporal(LogRepository repository, List<String> logsStr) {

    List<Log> logs = logsStr
        .stream()
        .map(Log::fromString)
        .collect(Collectors.toList());

    repository.addAll(logs);

  }
}
