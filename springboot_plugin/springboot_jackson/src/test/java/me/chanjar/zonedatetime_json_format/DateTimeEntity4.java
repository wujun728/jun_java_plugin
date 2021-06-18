package me.chanjar.zonedatetime_json_format;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.time.ZoneId;
import java.util.Date;

/**
 * Created by qianjia on 2017/7/18.
 */
public class DateTimeEntity4 implements Entity {

  @JsonFormat(pattern = "yyyy-MMM-dd HH:mm:ss Z", with = { JsonFormat.Feature.WRITE_DATES_WITH_ZONE_ID })
  private DateTime jodaDateTime;

  public DateTimeEntity4() {
  }

  public DateTimeEntity4(Date date) {
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
    return "yyyy-MMM-dd HH:mm:ss ZZZ, enable: JsonFormat.Feature.WRITE_DATES_WITH_ZONE_ID";
  }
}
