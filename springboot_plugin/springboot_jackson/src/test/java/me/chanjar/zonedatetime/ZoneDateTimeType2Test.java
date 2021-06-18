package me.chanjar.zonedatetime;

import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = { ZoneDateTimeType2Test.class, JacksonAutoConfiguration.class }, properties = {
    "spring.jackson.serialization.write_dates_as_timestamps=false",
    "spring.jackson.serialization.write_dates_with_zone_id=true",
})
public class ZoneDateTimeType2Test extends ZoneDateTimeTypeTestBase {

}
