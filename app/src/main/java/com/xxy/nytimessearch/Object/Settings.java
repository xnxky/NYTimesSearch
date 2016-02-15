package com.xxy.nytimessearch.Object;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by xiangyang_xiao on 2/12/16.
 */

public class Settings {

  public final static String DATE_FORMAT = "yyyyMMdd";
  public static List<String> sortOrderValues =
      new ArrayList<>();

  static {
    for (SortOrder order : SortOrder.values()) {
      sortOrderValues.add(order.name());
    }
  }

  private String startDate; //format yyyyMMdd
  private String endDate;
  private SortOrder sortOrder;
  private Set<String> newsDesk;

  public Settings() {
    sortOrder = SortOrder.Newest;
    startDate = new LocalDate().minusWeeks(1).toString(Settings.DATE_FORMAT);
    endDate = new LocalDate().toString(Settings.DATE_FORMAT);
    newsDesk = new HashSet<>();
  }

  public Settings(
      SortOrder sortOrder,
      String startDate,
      String endDate,
      Set<String> newsDesk) {
    this.sortOrder = sortOrder;
    this.startDate = startDate;
    this.endDate = endDate;
    this.newsDesk = newsDesk;
  }

  public static int[] getDate(String date) {
    int year = Integer.valueOf(date.substring(0, 4));
    int month = Integer.valueOf(date.substring(4, 6)) - 1;
    int day = Integer.valueOf(date.substring(6));
    return new int[]{year, month, day};
  }

  public static String getDateString(
      int year, int month, int day
  ) {
    String yearString = String.valueOf(year);
    String monthString = month < 9 ?
        "0" + String.valueOf(month + 1) : String.valueOf(month + 1);
    String dayString = day < 10 ?
        "0" + String.valueOf(day) : String.valueOf(day);
    return yearString + monthString + dayString;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public SortOrder getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(SortOrder sortOrder) {
    this.sortOrder = sortOrder;
  }

  public Set<String> getNewsDesk() {
    return newsDesk;
  }

  public enum SortOrder {
    Newest, Oldest
  }

}
