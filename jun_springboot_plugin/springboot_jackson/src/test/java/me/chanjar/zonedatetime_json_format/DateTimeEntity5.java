package me.chanjar.zonedatetime_json_format;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.time.ZoneId;
import java.util.Date;

/**
 * Created by qianjia on 2017/7/18.
 */
public class DateTimeEntity5 implements Entity {

  @JsonFormat(pattern = "yyyy-MMM-dd HH:mm:ss",
      with = { JsonFormat.Feature.WRITE_DATES_WITH_ZONE_ID })
//  @JsonFormat(pattern = "yyyy-MMM-dd HH:mm:ss",
//      with = { JsonFormat.Feature.WRITE_DATES_WITH_ZONE_ID },
//      without = { JsonFormat.Feature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE })
//  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ", with = { JsonFormat.Feature.WRITE_DATES_WITH_ZONE_ID })
  private DateTime jodaDateTime;

  public DateTimeEntity5() {
  }

  public DateTimeEntity5(Date date) {
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
    return "yyyy-MMM-dd HH:mm:ss, enable: JsonFormat.Feature.WRITE_DATES_WITH_ZONE_ID";
  }
}
