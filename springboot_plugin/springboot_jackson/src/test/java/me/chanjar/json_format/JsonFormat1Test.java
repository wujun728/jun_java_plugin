package me.chanjar.json_format;

import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = { JsonFormat1Test.class, JacksonAutoConfiguration.class })
public class JsonFormat1Test extends JsonFormatTestBase {

}
