package me.chanjar.zonedatetime_json_format;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.time.ZoneId;
import java.util.Date;

/**
 * Created by qianjia on 2017/7/18.
 */
public class DateTimeEntity6 implements Entity {

  @JsonFormat(with = { JsonFormat.Feature.WRITE_DATES_WITH_ZONE_ID })
  private DateTime jodaDateTime;

  public DateTimeEntity6() {
  }

  public DateTimeEntity6(Date date) {
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
    return "null, enable: JsonFormat.Feature.WRITE_DATES_WITH_ZONE_ID";
  }
}
