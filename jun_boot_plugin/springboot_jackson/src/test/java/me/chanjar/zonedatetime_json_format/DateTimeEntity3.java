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
public class DateTimeEntity3 implements Entity {

  @JsonFormat(pattern = "yyyy-MMM-dd HH:mm:ss ZZZ")
  private DateTime jodaDateTime;

  public DateTimeEntity3() {
  }

  public DateTimeEntity3(Date date) {
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
    return "yyyy-MMM-dd HH:mm:ss ZZZ";
  }
}
