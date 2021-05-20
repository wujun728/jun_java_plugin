package com.jun.plugin.json.jackson3.tutorials;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dzy on 2018/9/1
 */
public class PersonAnyGetter {
  private Map<String,Object> props = new HashMap<>();

  @JsonAnyGetter
  public Map<String,Object> propties(){
    return props;
  }

  @Override
  public String toString() {
    return "PersonAnyGetter{" +
      "props=" + props +
      '}';
  }
}
