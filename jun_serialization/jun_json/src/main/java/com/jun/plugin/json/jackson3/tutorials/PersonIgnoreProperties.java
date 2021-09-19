package com.jun.plugin.json.jackson3.tutorials;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by dzy on 2018/9/1
 */
@JsonIgnoreProperties({"lastName","firstName"})
public class PersonIgnoreProperties {
  public long    personId = 0;
  public String  firstName = null;
  public String  lastName  = null;
}
