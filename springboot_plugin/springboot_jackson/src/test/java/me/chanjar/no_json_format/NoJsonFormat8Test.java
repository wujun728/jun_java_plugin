package me.chanjar.no_json_format;

import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = { NoJsonFormat8Test.class, JacksonAutoConfiguration.class }, properties = {
    "spring.jackson.date-format=yyyy-MMM-dd HH:mm:ss",
    "spring.jackson.joda-date-time-format=yyyy-MMM-dd'T'HH:mm:ss"
})
public class NoJsonFormat8Test extends NoJsonFormatTestBase {

}
