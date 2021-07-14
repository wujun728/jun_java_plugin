package com.jun.plugin.json.jackson3.tutorials;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by dzy on 2018/9/1
 */
public class PersonIgnore {
  @JsonIgnore
  public long personId = 0;

  public String name = null;
}
