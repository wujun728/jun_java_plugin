package com.jun.plugin.json.jackson3.tutorials;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

/**
 * Created by dzy on 2018/9/1
 */
public class PersonIgnoreType {
  @JsonIgnoreType
  public static class Address {
    public String streetName  = null;
    public String houseNumber = null;
    public String zipCode     = null;
    public String city        = null;
    public String country     = null;
  }

  public long    personId = 0;

  public String  name = null;

  public Address address = null;
}
