package me.chanjar.json_format;

import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = { JsonFormat3Test.class, JacksonAutoConfiguration.class }, properties = {
    "spring.jackson.date-format=yyyy-MM-dd HH:mm:ss"
})
public class JsonFormat3Test extends JsonFormatTestBase {

}
