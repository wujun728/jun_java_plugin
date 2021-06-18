package me.chanjar.json_format;

import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = { JsonFormat2Test.class, JacksonAutoConfiguration.class }, properties = {
    "spring.jackson.serialization.write_dates_as_timestamps=false"
})
public class JsonFormat2Test extends JsonFormatTestBase {

}
