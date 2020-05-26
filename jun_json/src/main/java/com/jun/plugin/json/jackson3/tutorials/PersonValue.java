package com.jun.plugin.json.jackson3.tutorials;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by dzy on 2018/9/1
 */
public class PersonValue {
  public long   personId = 0;
  public String name = null;

  @JsonValue
  public String toJson(){
    return this.personId + "," + this.name;
  }
}
