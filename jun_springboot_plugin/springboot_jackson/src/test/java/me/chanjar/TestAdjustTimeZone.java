package me.chanjar;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Created by qianjia on 2017/7/19.
 */
public class TestAdjustTimeZone {

  public static void main(String[] args) throws IOException {

    serializeInconsistent();

    deserializeWrongTimeZone();

  }

  private static void serializeInconsistent() throws JsonProcessingException {

    LocalDateTime localDateTime = new LocalDateTime(2017, 1, 1, 1, 1, 1);

    ZonedDateTime java8ZonedDateTime = ZonedDateTime.ofInstant(localDateTime.toDate().toInstant(), ZoneId.of("Asia/Shanghai"));
    DateTime jodaDateTime = localDateTime.toDateTime(DateTimeZone.forID("Asia/Shanghai"));

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JodaModule());
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    System.out.println(objectMapper.writeValueAsString(java8ZonedDateTime));
    System.out.println(objectMapper.writeValueAsString(jodaDateTime));

  }

  public static class Foo {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private DateTime jodaDateTime;

    public DateTime getJodaDateTime() {
      return jodaDateTime;
    }

    public void setJodaDateTime(DateTime jodaDateTime) {
      this.jodaDateTime = jodaDateTime;
    }
  }

  private static void deserializeWrongTimeZone() throws IOException {

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JodaModule());
    objectMapper.enable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

    System.out.println(objectMapper.readValue("{\"jodaDateTime\":\"2017-01-01 01:01:01[Asia/Shanghai]\"}", Foo.class).getJodaDateTime());

  }

}
