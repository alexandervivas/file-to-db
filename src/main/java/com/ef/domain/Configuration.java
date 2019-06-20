package com.ef.domain;

import com.google.common.collect.Iterables;
import com.google.common.collect.Streams;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Configuration {

  private LocalDate startDate;
  private DurationEnum duration;
  private Integer threshold;

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
        }
      }

      return Optional.of(configuration);

    }

    return Optional.empty();

  }

  private static boolean validArgumentsFormat(List<String> args) {

    for (int index = 0; index < args.size(); index++) {

      if (isArgumentName(index) && !args.get(index).startsWith("--")
          || isArgumentValue(index) && args.get(index).startsWith("--")) {

        return false;

      }

    }

    return true;

  }

  private static boolean isArgumentName(int index) {

    return index % 2 == 0;

  }

  private static boolean isArgumentValue(int index) {

    return index % 2 != 0;

  }

  private static List<Argument> getArguments(List<String> args) {

    return Streams
        .stream(Iterables.partition(args, 2))
        .map(a -> new Argument(ArgumentEnum.fromString(a.get(0)), a.get(1)))
        .collect(Collectors.toList());

  }

}
