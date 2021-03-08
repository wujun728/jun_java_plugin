package me.chanjar.no_json_format;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.Assert.assertEquals;

public class NoJsonFormatTestBase extends AbstractTestNGSpringContextTests {

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void test() throws Exception {
    Foo sampleFoo = new Foo(new LocalDateTime(2017, 1, 1, 1, 1, 1).toDate());

    doTest(sampleFoo.getUtilDate(), Date.class);

    doTest(sampleFoo.getJava8LocalDate(), java.time.LocalDate.class);
    doTest(sampleFoo.getJava8LocalTime(), java.time.LocalTime.class);
    doTest(sampleFoo.getJava8LocalDateTime(), java.time.LocalDateTime.class);
    doTest(sampleFoo.getJava8ZonedDateTime(), java.time.ZonedDateTime.class);

    doTest(sampleFoo.getJodaLocalDate(), org.joda.time.LocalDate.class);
    doTest(sampleFoo.getJodaLocalTime(), org.joda.time.LocalTime.class);
    doTest(sampleFoo.getJodaLocalDateTime(), org.joda.time.LocalDateTime.class);
    doTest(sampleFoo.getJodaDateTime(), org.joda.time.DateTime.class);

  }

  private <T> void doTest(T value, Class<T> clazz) throws Exception {

    String json = objectMapper.writeValueAsString(value);
    System.out.println("Serialize " + clazz.getName() + ":");
    System.out.println(json);

    try {
      T result = objectMapper.readValue(json, clazz);
      assertEquals(result, value);
      System.out.println("Deserialize success");
    } catch (AssertionError | Exception e) {
      System.out.println("Deserialize failed");
      System.out.println(ExceptionUtils.getStackTrace(e));
    }
    System.out.println();
  }

  @Autowired
  public void setObjectMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }
}
