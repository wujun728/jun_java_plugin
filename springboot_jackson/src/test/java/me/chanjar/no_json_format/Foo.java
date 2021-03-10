package me.chanjar.no_json_format;

import org.joda.time.DateTime;

import java.time.*;
import java.util.Date;

/**
 * Created by qianjia on 2017/7/18.
 */
public class Foo {

  private Date utilDate;

  private LocalDateTime java8LocalDateTime;

  private ZonedDateTime java8ZonedDateTime;

  private LocalDate java8LocalDate;

  private LocalTime java8LocalTime;

  private org.joda.time.LocalDateTime jodaLocalDateTime;

  private org.joda.time.DateTime jodaDateTime;

  private org.joda.time.LocalDate jodaLocalDate;

  private org.joda.time.LocalTime jodaLocalTime;

  public Foo() {
  }

  public Foo(Date date) {

    this.utilDate = date;

    this.java8LocalDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    this.java8LocalDate = java8LocalDateTime.toLocalDate();
    this.java8LocalTime = java8LocalDateTime.toLocalTime();
    this.java8ZonedDateTime = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());

    this.jodaLocalDateTime = org.joda.time.LocalDateTime.fromDateFields(date);
    this.jodaLocalDate = jodaLocalDateTime.toLocalDate();
    this.jodaLocalTime = jodaLocalDateTime.toLocalTime();
    this.jodaDateTime = jodaLocalDateTime.toDateTime();
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

  public DateTime getJodaDateTime() {
    return jodaDateTime;
  }

  public ZonedDateTime getJava8ZonedDateTime() {
    return java8ZonedDateTime;
  }

  public void setJava8ZonedDateTime(ZonedDateTime java8ZonedDateTime) {
    this.java8ZonedDateTime = java8ZonedDateTime;
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

  public void setJodaDateTime(DateTime jodaDateTime) {
    this.jodaDateTime = jodaDateTime;
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
