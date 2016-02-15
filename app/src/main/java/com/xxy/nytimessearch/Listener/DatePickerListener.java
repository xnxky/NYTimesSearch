package com.xxy.nytimessearch.Listener;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

/**
 * Created by xiangyang_xiao on 2/14/16.
 */
public class DatePickerListener implements DatePickerDialog.OnDateSetListener {

  public int getYear() {
    return year;
  }

  public int getMonth() {
    return month;
  }

  public int getDay() {
    return day;
  }

  int year;
  int month;
  int day;

  public DatePickerListener (
      int year, int month, int day
  ) {
    this.year = year;
    this.month = month;
    this.day = day;
  }

  // handle the date selected
  @Override
  public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
    this.year = year;
    this.month = monthOfYear;
    this.day = dayOfMonth;
  }
}
