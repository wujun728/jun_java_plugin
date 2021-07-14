package com.jun.plugin.json.jackson3.tutorials;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dzy on 2018/9/1
 */
public class Bag {
  private Map<String, Object> properties = new HashMap<>();

  @JsonAnySetter
  public void set(String fieldName, Object value){
    this.properties.put(fieldName, value);
  }

  public Object get(String fieldName){
    return this.properties.get(fieldName);
  }

  @Override
  public String toString() {
    return "Bag{" +
      "properties=" + properties +
      '}';
  }
}
