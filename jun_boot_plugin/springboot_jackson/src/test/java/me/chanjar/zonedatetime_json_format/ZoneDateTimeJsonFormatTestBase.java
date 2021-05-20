package me.chanjar.zonedatetime_json_format;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.Assert.assertEquals;

public class ZoneDateTimeJsonFormatTestBase extends AbstractTestNGSpringContextTests {

  @Autowired
  private ObjectMapper objectMapper;

  @DataProvider
  public Object[][] testData() {
    Date date = new LocalDateTime(2017, 1, 1, 1, 1, 1).toDate();
    return new Object[][] {
        new Object[] { new DateTimeEntity1(date) },
        new Object[] { new DateTimeEntity2(date) },
        new Object[] { new DateTimeEntity3(date) },
        new Object[] { new DateTimeEntity4(date) },
        new Object[] { new DateTimeEntity5(date) },
        new Object[] { new DateTimeEntity6(date) },
        new Object[] { new ZonedDateTimeEntity1(date) },
        new Object[] { new ZonedDateTimeEntity2(date) },
        new Object[] { new ZonedDateTimeEntity3(date) },
        new Object[] { new ZonedDateTimeEntity4(date) },
        new Object[] { new ZonedDateTimeEntity5(date) },
        new Object[] { new ZonedDateTimeEntity6(date) },
    };
  }

  @Test(dataProvider = "testData")
  public void test(Entity sampleEntity) throws Exception {

    DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MMM-dd HH:mm:ss").withOffsetParsed().withZone(DateTimeZone.forID("Asia/Shanghai"));
    int columnSize = 15;
    System.out.println(StringUtils.rightPad("Data:", columnSize, ' ') + dateTimeFormatter.parseDateTime("2017-一月-01 01:01:01"));
    String json = objectMapper.writeValueAsString(sampleEntity);
    System.out.println(StringUtils.rightPad("Class:", columnSize, ' ') + sampleEntity.getValue().getClass().getName() + ":");
    System.out.println(StringUtils.rightPad("Pattern:", columnSize, ' ') + sampleEntity.getPattern());
    System.out.println(StringUtils.rightPad("Ser result:", columnSize, ' ') + json);

    try {
      Entity result = objectMapper.readValue(json, sampleEntity.getClass());
      assertEquals(result.getValue(), sampleEntity.getValue());
      System.out.println(StringUtils.rightPad("Deser result:", columnSize, ' ') + "success");
    } catch (AssertionError e) {
      System.out.println(StringUtils.rightPad("Deser result:", columnSize, ' ') + "success but assert failed");
      System.out.println(StringUtils.rightPad("Message:", columnSize, ' ') + e.getClass().getName() + ": " + e.getMessage());
    } catch (Exception e) {
      System.out.println(StringUtils.rightPad("Deser result:", columnSize, ' ') + "failed");
      System.out.println(StringUtils.rightPad("Message:", columnSize, ' ') + e.getClass().getName() + ": " + e.getMessage());
    }
    System.out.println();

  }

  @Autowired
  public void setObjectMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }
}
