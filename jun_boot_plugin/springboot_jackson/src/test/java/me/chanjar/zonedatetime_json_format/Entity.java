package me.chanjar.zonedatetime_json_format;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by qianjia on 2017/7/19.
 */
public interface Entity {

  @JsonIgnore
  Object getValue();

  @JsonIgnore
  String getPattern();

}
