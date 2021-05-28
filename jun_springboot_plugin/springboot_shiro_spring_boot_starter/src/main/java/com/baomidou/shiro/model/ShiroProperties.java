package com.baomidou.shiro.model;

import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * shiro的配置参数
 *
 * @author Wujun
 */
@Data
@ConfigurationProperties(prefix = "shiro")
public class ShiroProperties {

  /**
   * 登录地址
   */
  private String loginUrl = "/login";
  /**
   * 成功地址
   */
  private String successUrl = "/success";
  /**
   * 无权限地址
   */
  private String unauthorizedUrl = "/unauthorized";
  /**
   * filterChain配置
   */
  private Map<String, String> filterChain;
  /**
   * session配置
   */
  @NestedConfigurationProperty
  private SessionDefinition session = new SessionDefinition();
  /**
   * rememberMe配置
   */
  @NestedConfigurationProperty
  private RememberMeDefinition rememberMe = new RememberMeDefinition();
  /**
   * 密码匹配器
   */
  @NestedConfigurationProperty
  private CredentialsMatcherDefinition credentialsMatcher = new CredentialsMatcherDefinition();
  /**
   * 密码错误锁用户配置
   */
  @NestedConfigurationProperty
  private LockDefinition lock = new LockDefinition();

}
