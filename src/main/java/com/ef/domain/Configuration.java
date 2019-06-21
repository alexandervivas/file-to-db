package com.ef.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Configuration {

  private LocalDate startDate;
  private DurationEnum duration;
  private Integer threshold;
  private String accessLog;

  private Configuration() {

  }

  public static Optional<Configuration> fromCommandLineArguments(List<String> args) {

    if (validArgumentsFormat(args)) {

      Configuration configuration = new Configuration();

      for (Argument argument : getArguments(args)) {
        switch (argument.getKey()) {
          case DURATION:
            configuration.duration = DurationEnum.fromString(argument.getValue());
            break;
          case THRESHOLD:
            configuration.threshold = Integer.parseInt(argument.getValue());
            break;
          case START_DATE:
            configuration.startDate = LocalDate.parse(argument.getValue(), DateTimeFormatter
                .ofPattern("yyyy-MM-dd.HH:mm:ss"));// TODO read this from a config file
            break;
          case ACCESSLOG:
            configuration.accessLog = argument.getValue();
            break;
        }
      }

      return Optional.of(configuration);

    }

    return Optional.empty();

  }

  private static boolean validArgumentsFormat(List<String> args) {

    return !args.stream().anyMatch(
        arg -> !arg.startsWith("--")
            || arg.split("--").length > 2
            || arg.split("=").length != 2
    );

  }

  private static List<Argument> getArguments(List<String> args) {

    return args
        .stream()
        .map(a -> {
          String[] argument = a.split("=");
          return new Argument(ArgumentEnum.fromString(argument[0]), argument[1]);
        })
        .collect(Collectors.toList());

  }

  public String getAccessLog() {
    return accessLog;
  }
}
