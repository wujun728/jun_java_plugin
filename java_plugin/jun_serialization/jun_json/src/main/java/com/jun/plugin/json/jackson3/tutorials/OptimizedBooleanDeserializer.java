package com.jun.plugin.json.jackson3.tutorials;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * Created by dzy on 2018/9/1
 */
public class OptimizedBooleanDeserializer extends JsonDeserializer {

  @Override
  public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
    return "1".equals(p.getText());
  }
}
