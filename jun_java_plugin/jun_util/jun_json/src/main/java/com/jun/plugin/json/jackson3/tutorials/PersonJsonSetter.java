package com.jun.plugin.json.jackson3.tutorials;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Created by dzy on 2018/9/1
 */
public class PersonJsonSetter {
  private long   personId = 0;
  private String name     = null;

  public long getPersonId() { return this.personId; }
  @JsonSetter("id")
  public void setPersonId(long personId) { this.personId = personId; }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  @Override
  public String toString() {
    return "PersonJsonSetter{" +
      "personId=" + personId +
      ", name='" + name + '\'' +
      '}';
  }
}
