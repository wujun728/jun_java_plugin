package me.chanjar.zonedatetime_json_format;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.time.ZoneId;
import java.util.Date;

/**
 * Created by qianjia on 2017/7/18.
 */
public class DateTimeEntity2 implements Entity {

  @JsonFormat(pattern = "yyyy-MMM-dd HH:mm:ss")
  private DateTime jodaDateTime;

  public DateTimeEntity2() {
  }

  public DateTimeEntity2(Date date) {
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
    return "yyyy-MMM-dd HH:mm:ss";
  }
}
