package com.jun.plugin.json.jackson3.tutorials;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * Created by dzy on 2018/9/1
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PersonAutoDetect {
  private long  personId = 123;
  public String name     = null;
}
