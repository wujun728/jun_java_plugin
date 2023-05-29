package com.jun.plugin.json.jackson3.tutorials;

import com.fasterxml.jackson.annotation.JsonRawValue;

/**
 * Created by dzy on 2018/9/1
 */
public class PersonRawValue {
  public long   personId = 0;

  @JsonRawValue
  public String address  = "{ \"street\" : \"Wall Street\", \"no\":1}";

}
