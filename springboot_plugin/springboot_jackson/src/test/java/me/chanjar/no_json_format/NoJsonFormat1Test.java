package me.chanjar.no_json_format;

import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = { NoJsonFormat1Test.class, JacksonAutoConfiguration.class })
public class NoJsonFormat1Test extends NoJsonFormatTestBase {

}
