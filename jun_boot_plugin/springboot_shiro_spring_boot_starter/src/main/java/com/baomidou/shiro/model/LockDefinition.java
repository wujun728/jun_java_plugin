package com.baomidou.shiro.model;

import lombok.Data;

/**
 * 锁用户配置
 *
 * @author Wujun
 */
@Data
public class LockDefinition {

  /**
   * 是否开启重试限制,默认开启
   */
  private boolean enabled = false;
  /**
   * 重试次数，默认5次锁用户
   */
  private int retryLimit = 5;
  /**
   * 重试缓存名称
   */
  private String cacheName = "passwordRetryCache";

}
