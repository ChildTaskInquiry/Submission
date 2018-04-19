package com;

import java.util.Calendar;

public class DayWorkCalendar {

  public Calendar calculateDayWork(Calendar date, int days) {
    if (days > 0) {
      for (int i = 0; i < days; i++) {
        date.add(Calendar.DAY_OF_MONTH, 1);
        if (date.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
            || date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
          i--;
        }
      }
    } else if (days < 0) {
      for (int i = 0; i > days; i--) {
        date.add(Calendar.DAY_OF_MONTH, -1);
        if (date.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
            || date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
          i++;
        }
      }
    }
    return date;
  }

}
