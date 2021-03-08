package me.chanjar.json_format;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by qianjia on 2017/7/18.
 */
public class Foo {

  @JsonFormat(pattern = "yyyy-MMM-dd HH:mm:ss")
  private Date utilDate;

  @JsonFormat(pattern = "yyyy-MMM-dd HH:mm:ss")
  private LocalDateTime java8LocalDateTime;

  @JsonFormat(pattern = "yyyy-MMM-dd")
  private LocalDate java8LocalDate;

  @JsonFormat(pattern = "HH:mm:ss")
  private LocalTime java8LocalTime;

  @JsonFormat(pattern = "yyyy-MMM-dd HH:mm:ss")
  private org.joda.time.LocalDateTime jodaLocalDateTime;

  @JsonFormat(pattern = "yyyy-MMM-dd")
  private org.joda.time.LocalDate jodaLocalDate;

  @JsonFormat(pattern = "HH:mm:ss")
  private org.joda.time.LocalTime jodaLocalTime;

  public Foo() {
  }

  public Foo(Date date) {

    this.utilDate = date;

    this.java8LocalDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    this.java8LocalDate = java8LocalDateTime.toLocalDate();
    this.java8LocalTime = java8LocalDateTime.toLocalTime();

    this.jodaLocalDateTime = org.joda.time.LocalDateTime.fromDateFields(date);
    this.jodaLocalDate = jodaLocalDateTime.toLocalDate();
    this.jodaLocalTime = jodaLocalDateTime.toLocalTime();
  }

  public void setJava8LocalDateTime(LocalDateTime java8LocalDateTime) {
    this.java8LocalDateTime = java8LocalDateTime;
  }

  public void setJodaLocalDateTime(org.joda.time.LocalDateTime jodaLocalDateTime) {
    this.jodaLocalDateTime = jodaLocalDateTime;
  }

  public void setUtilDate(Date utilDate) {
    this.utilDate = utilDate;
  }

  public LocalDateTime getJava8LocalDateTime() {
    return java8LocalDateTime;
  }

  public Date getUtilDate() {
    return utilDate;
  }

  public org.joda.time.LocalDateTime getJodaLocalDateTime() {
    return jodaLocalDateTime;
  }

  public LocalDate getJava8LocalDate() {
    return java8LocalDate;
  }

  public void setJava8LocalDate(LocalDate java8LocalDate) {
    this.java8LocalDate = java8LocalDate;
  }

  public LocalTime getJava8LocalTime() {
    return java8LocalTime;
  }

  public void setJava8LocalTime(LocalTime java8LocalTime) {
    this.java8LocalTime = java8LocalTime;
  }

  public org.joda.time.LocalDate getJodaLocalDate() {
    return jodaLocalDate;
  }

  public void setJodaLocalDate(org.joda.time.LocalDate jodaLocalDate) {
    this.jodaLocalDate = jodaLocalDate;
  }

  public org.joda.time.LocalTime getJodaLocalTime() {
    return jodaLocalTime;
  }

  public void setJodaLocalTime(org.joda.time.LocalTime jodaLocalTime) {
    this.jodaLocalTime = jodaLocalTime;
  }

}
