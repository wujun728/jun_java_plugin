package me.chanjar.zonedatetime_json_format;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.time.ZoneId;
import java.util.Date;

/**
 * Created by qianjia on 2017/7/18.
 */
public class DateTimeEntity1 implements Entity {

  private DateTime jodaDateTime;

  public DateTimeEntity1() {
  }

  public DateTimeEntity1(Date date) {
    this.jodaDateTime = new DateTime(date.getTime(), DateTimeZone.forID(ZoneId.systemDefault().getId()));
  }

  public DateTime getJodaDateTime() {
    return jodaDateTime;
  }

  public void setJodaDateTime(DateTime jodaDateTime) {
    this.jodaDateTime = jodaDateTime;
  }

  @Override
  public Object getValue() {
    return this.jodaDateTime;
  }

  @Override
  public String getPattern() {
    return null;
  }
}
