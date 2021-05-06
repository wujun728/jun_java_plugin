package com.jun.plugin.json.jackson3.tutorials;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Created by dzy on 2018/9/1
 */
public class PersonGetter {
  @Override
  public String toString() {
    return "PersonGetter{" +
      "personId=" + personId +
      '}';
  }

  private long  personId = 0;

  @JsonGetter("id")
  public long personId() { return this.personId; }

  @JsonSetter("id")
  public void personId(long personId) { this.personId = personId; }
}
