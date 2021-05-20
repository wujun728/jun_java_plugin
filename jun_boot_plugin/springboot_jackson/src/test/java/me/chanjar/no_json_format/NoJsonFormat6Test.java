package me.chanjar.no_json_format;

import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = { NoJsonFormat6Test.class, JacksonAutoConfiguration.class }, properties = {
    "spring.jackson.date-format=yyyy-MM-dd HH:mm:ss",
    "spring.jackson.joda-date-time-format=yyyy-MM-dd HH:mm:ss",
    "spring.jackson.serialization.write_dates_as_timestamps=false",
    "spring.jackson.serialization.write_dates_with_zone_id=true",
    "spring.jackson.deserialization.adjust_dates_to_context_time_zone=false"
})
public class NoJsonFormat6Test extends NoJsonFormatTestBase {

}
