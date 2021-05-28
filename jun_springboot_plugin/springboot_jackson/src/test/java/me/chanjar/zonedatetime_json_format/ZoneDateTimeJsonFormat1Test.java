package me.chanjar.zonedatetime_json_format;

import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = { ZoneDateTimeJsonFormat1Test.class, JacksonAutoConfiguration.class }, properties = {
    "spring.jackson.serialization.write_dates_as_timestamps=false"
})
public class ZoneDateTimeJsonFormat1Test extends ZoneDateTimeJsonFormatTestBase {

}
