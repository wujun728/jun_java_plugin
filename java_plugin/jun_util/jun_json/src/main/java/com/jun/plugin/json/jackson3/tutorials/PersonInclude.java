package com.jun.plugin.json.jackson3.tutorials;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Created by dzy on 2018/9/1
 */
@JsonPropertyOrder({"name", "personId", "Key3"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PersonInclude {
  public long  personId = 0;
  public String name     = null;

  @Override
  public String toString() {
    return "PersonInclude{" +
      "personId=" + personId +
      ", name='" + name + '\'' +
      '}';
  }
}
