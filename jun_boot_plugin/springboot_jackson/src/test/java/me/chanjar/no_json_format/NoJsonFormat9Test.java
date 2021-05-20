package me.chanjar.no_json_format;

import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = { NoJsonFormat9Test.class, JacksonAutoConfiguration.class }, properties = {
    "spring.jackson.joda-date-time-format=yyyy-MM-dd'T'HH:mm:ss"
})
public class NoJsonFormat9Test extends NoJsonFormatTestBase {

}
