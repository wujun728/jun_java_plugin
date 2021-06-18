package me.chanjar.json_format;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class JsonFormatTestBase extends AbstractTestNGSpringContextTests {

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void test() throws Exception {
    Foo sampleFoo = new Foo(new LocalDateTime(2017, 1, 1, 1, 1, 1).toDate());

    String json = objectMapper.writeValueAsString(sampleFoo);
    System.out.println("Serialize " + Foo.class.getName() + ":");
    System.out.println(json);

    Foo result = objectMapper.readValue(json, Foo.class);

    doAssertEquals(result.getUtilDate(), sampleFoo.getUtilDate());
    doAssertEquals(result.getJava8LocalDate(), sampleFoo.getJava8LocalDate());
    doAssertEquals(result.getJava8LocalTime(), sampleFoo.getJava8LocalTime());
    doAssertEquals(result.getJava8LocalDateTime(), sampleFoo.getJava8LocalDateTime());
    doAssertEquals(result.getJodaLocalDate(), sampleFoo.getJodaLocalDate());
    doAssertEquals(result.getJodaLocalTime(), sampleFoo.getJodaLocalTime());
    doAssertEquals(result.getJodaLocalDateTime(), sampleFoo.getJodaLocalDateTime());

  }

  private void doAssertEquals(Object acutal, Object expected) throws Exception {

    try {
      assertEquals(acutal, expected);
      System.out.println("Deserialize success: " + acutal.getClass().getName());
    } catch (AssertionError | Exception e) {
      System.out.println("Deserialize failed: " + acutal.getClass().getName());
      System.out.println(ExceptionUtils.getStackTrace(e));
    }
    System.out.println();

  }

  @Autowired
  public void setObjectMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }
}
