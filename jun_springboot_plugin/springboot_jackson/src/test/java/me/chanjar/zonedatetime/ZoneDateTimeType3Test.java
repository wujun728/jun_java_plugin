package me.chanjar.zonedatetime;

import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = { ZoneDateTimeType3Test.class, JacksonAutoConfiguration.class }, properties = {
    "spring.jackson.serialization.write_dates_as_timestamps=false",
    "spring.jackson.serialization.write_dates_with_zone_id=true",
    "spring.jackson.deserialization.adjust_dates_to_context_time_zone=false"
})
public class ZoneDateTimeType3Test extends ZoneDateTimeTypeTestBase {

}
