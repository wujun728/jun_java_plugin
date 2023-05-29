package com.jun.plugin.json.jackson3.tutorials;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Created by dzy on 2018/9/1
 */
public class PersonDeserialize {
  public long    id      = 0;
  public String  name    = null;

  @JsonDeserialize(using = OptimizedBooleanDeserializer.class)
  public boolean enabled = false;

  @Override
  public String toString() {
    return "PersonDeserialize{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", enabled=" + enabled +
      '}';
  }
}
