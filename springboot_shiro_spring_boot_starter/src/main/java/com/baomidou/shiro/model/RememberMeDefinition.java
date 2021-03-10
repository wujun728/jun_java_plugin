package com.baomidou.shiro.model;

import lombok.Data;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.Cookie;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * rememberMe配置参数
 *
 * @author Wujun
 */
@Data
public class RememberMeDefinition {

  /**
   * cookie配置
   */
  @NestedConfigurationProperty
  private CookieDefinition cookie = new CookieDefinition(
      CookieRememberMeManager.DEFAULT_REMEMBER_ME_COOKIE_NAME,
      Cookie.ONE_YEAR);

}