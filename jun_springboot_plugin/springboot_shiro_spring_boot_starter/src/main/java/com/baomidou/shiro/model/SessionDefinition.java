package com.baomidou.shiro.model;

import lombok.Data;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * session配置参数
 *
 * @author Wujun
 */
@Data
public class SessionDefinition {

  /**
   * 是否开启session， 默认开启,无状态应用应关闭
   */
  private boolean enabled = true;
  /**
   * 是否生成cookie，默认开启
   */
  private boolean sessionIdCookieEnabled = true;
  /**
   * 是否开启重写sessionIdUrl，默认开启
   */
  private boolean sessionIdUrlRewritingEnabled = true;
  /**
   * 是否删除不可用session，默认开启
   */
  private boolean deleteInvalidSessions = true;
  /**
   * session过期时间，默认半小时
   */
  private long timeOut = 1800000;
  /**
   * cookie配置
   */
  @NestedConfigurationProperty
  private CookieDefinition cookie = new CookieDefinition(ShiroHttpSession.DEFAULT_SESSION_ID_NAME,
      SimpleCookie.DEFAULT_MAX_AGE);

}