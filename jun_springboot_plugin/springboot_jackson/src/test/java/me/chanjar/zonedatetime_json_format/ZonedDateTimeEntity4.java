package me.chanjar.zonedatetime_json_format;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Created by qianjia on 2017/7/18.
 */
public class ZonedDateTimeEntity4 implements Entity {

  @JsonFormat(pattern = "yyyy-MMM-dd HH:mm:ss ZZZ",
      with = { JsonFormat.Feature.WRITE_DATES_WITH_ZONE_ID },
      without = { JsonFormat.Feature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE })
  private ZonedDateTime java8ZonedDateTime;

  public ZonedDateTimeEntity4() {
  }

  public ZonedDateTimeEntity4(Date date) {

    this.java8ZonedDateTime = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
  }

  public ZonedDateTime getJava8ZonedDateTime() {
    return java8ZonedDateTime;
  }

  public void setJava8ZonedDateTime(ZonedDateTime java8ZonedDateTime) {
    this.java8ZonedDateTime = java8ZonedDateTime;
  }

  @Override
  public Object getValue() {
    return this.java8ZonedDateTime;
  }

  @Override
  public String getPattern() {
    return "yyyy-MMM-dd HH:mm:ss ZZZ, eanble: JsonFormat.Feature.WRITE_DATES_WITH_ZONE_ID, disable: JsonFormat.Feature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE";
  }
}
