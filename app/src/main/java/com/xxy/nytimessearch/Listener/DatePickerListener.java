package com.xxy.nytimessearch.Listener;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;

import com.xxy.nytimessearch.Object.Settings;

/**
 * Created by xiangyang_xiao on 2/14/16.
 */
public class DatePickerListener implements DatePickerDialog.OnDateSetListener {

  TextView tvDate;

  public DatePickerListener (TextView tvDate) {
    this.tvDate = tvDate;
  }

  public void setView(TextView tvDate) {
    this.tvDate = tvDate;
  }

  // handle the date selected
  @Override
  public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
    tvDate.setText(Settings.getDateString(year, monthOfYear, dayOfMonth));
  }

  public int[] getDate() {
    return Settings.getDate(tvDate.getText().toString());
  }
}
