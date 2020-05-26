package com.jun.plugin.json.jackson3.model;

/**
 * Created by dzy on 2018/8/31
 */
public class Address {
  private String street;
  private String city;
  private int zipcode;

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public int getZipcode() {
    return zipcode;
  }

  public void setZipcode(int zipcode) {
    this.zipcode = zipcode;
  }

  @Override
  public String toString(){
    return getStreet() + ", "+getCity()+", "+getZipcode();
  }
}
