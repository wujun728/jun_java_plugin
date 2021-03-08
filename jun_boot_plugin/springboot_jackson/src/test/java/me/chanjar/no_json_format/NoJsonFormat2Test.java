package me.chanjar.no_json_format;

import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = { NoJsonFormat2Test.class, JacksonAutoConfiguration.class }, properties = {
    "spring.jackson.date-format=yyyy-MM-dd HH:mm:ss"
})
public class NoJsonFormat2Test extends NoJsonFormatTestBase {

}
