package com.xxy.nytimessearch.Object;

import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by xiangyang_xiao on 2/12/16.
 */
public class Settings implements Serializable {

  public final static String DATE_FORMAT = "MM/dd/yyyy";

  private String date; //format MM/dd/yyyy
  private SortOrder sortOrder;
  private Set<String> newsDesk;

  public enum SortOrder {
    Newest, Oldest
  }

  public static List<String> sortOrderValues =
      new ArrayList<>();

  static {
    for(SortOrder order : SortOrder.values()) {
      sortOrderValues.add(order.name());
    }
  }

  public static Settings defaultSettings = new Settings(
      SortOrder.Newest,
      new LocalDate().toString(Settings.DATE_FORMAT),
      new HashSet<String>());

  public Settings(SortOrder sortOrder, String date, Set<String> newsDesk) {
    this.sortOrder = sortOrder;
    this.date = date;
    this.newsDesk = newsDesk;
  }

  public void update(Settings other) {
    this.sortOrder = other.sortOrder;
    this.date = other.date;
    this.newsDesk.clear();
    this.newsDesk.addAll(other.newsDesk);
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

  public int[] getDate() {
    String[] dateArray = date.split("/");
    int year = Integer.valueOf(dateArray[2]);
    int month = Integer.valueOf(dateArray[0])-1;
    int day = Integer.valueOf(dateArray[1]);
    return new int[]{year, month, day};
  }

  public void setDate(
      int year, int month, int day
  ) {
    String yearString = String.valueOf(year);
    String monthString = month < 9 ?
        "0" + String.valueOf(month+1) : String.valueOf(month+1);
    String dayString = day < 10 ?
        "0" + String.valueOf(day) : String.valueOf(day);
    date = monthString+"/"+dayString+"/"+yearString;
  }

}
