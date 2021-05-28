package com.baomidou.shiro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * cookie配置参数
 *
 * @author Wujun
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CookieDefinition {

  /**
   * cookie名称
   */
  private String name;
  /**
   * cookie域，一般不设置
   */
  private String domain;
  /**
   * cookie路径
   */
  private String path;
  /**
   * cookie失效时间
   */
  private int maxAge;
  /**
   * cookie是否为httpOnly，默认是
   */
  private boolean httpOnly = true;

  public CookieDefinition(String name, int maxAge) {
    this.name = name;
    this.maxAge = maxAge;
  }

}
