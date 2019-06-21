package com.ef.util;

import com.ef.domain.DurationEnum;
import java.time.LocalDateTime;

public class DateUtil {

  public static LocalDateTime getEndDate(LocalDateTime startDate, DurationEnum duration) {

    switch (duration) {
      case DAILY:
        return startDate.plusDays(1);
      case HOURLY:
        return startDate.plusHours(1);
    }

    return startDate;

  }

}
