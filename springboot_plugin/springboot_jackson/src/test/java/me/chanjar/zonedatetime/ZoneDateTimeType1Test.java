package me.chanjar.zonedatetime;

import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = { ZoneDateTimeType1Test.class, JacksonAutoConfiguration.class }, properties = {
    "spring.jackson.serialization.write_dates_as_timestamps=false",
})
public class ZoneDateTimeType1Test extends ZoneDateTimeTypeTestBase {

}
