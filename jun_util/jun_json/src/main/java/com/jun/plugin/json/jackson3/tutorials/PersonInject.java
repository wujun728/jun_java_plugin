package com.jun.plugin.json.jackson3.tutorials;

import com.fasterxml.jackson.annotation.JacksonInject;

/**
 * Created by dzy on 2018/9/1
 */
public class PersonInject {
  public long   id   = 0;
  public String name = null;

  @JacksonInject
  public String source = null;

  @Override
  public String toString() {
    return "PersonInject{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", source='" + source + '\'' +
      '}';
  }
}
