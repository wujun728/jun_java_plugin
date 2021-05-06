package com.jun.plugin.json.jackson3.tutorials;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by dzy on 2018/9/1
 */
public class PersonImmutable {
  private long   id   = 0;
  private String name = null;

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @JsonCreator
  public PersonImmutable(@JsonProperty("id") long id, @JsonProperty("name") String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public String toString() {
    return "PersonImmutable{" +
      "id=" + id +
      ", name='" + name + '\'' +
      '}';
  }
}
