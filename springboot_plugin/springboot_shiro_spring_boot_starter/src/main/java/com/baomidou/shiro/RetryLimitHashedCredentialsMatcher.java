package com.baomidou.shiro;

import java.util.concurrent.atomic.AtomicInteger;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;

/**
 * 重试验证器
 *
 * @author Wujun
 */
@SuppressWarnings("unchecked")
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

  /**
   * 重试缓存
   */
  private Cache<String, AtomicInteger> passwordRetryCache;
  /**
   * 重试次数
   */
  private int retryLimit;

  public RetryLimitHashedCredentialsMatcher(Cache passwordRetryCache, int retryLimit) {
    this.passwordRetryCache = passwordRetryCache;
    this.retryLimit = retryLimit;
  }

  @Override
  public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
    String username = (String) token.getPrincipal();
    AtomicInteger retryCount = passwordRetryCache.get(username);
    if (retryCount == null) {
      retryCount = new AtomicInteger(0);
      passwordRetryCache.put(username, retryCount);
    }
    if (retryCount.incrementAndGet() > retryLimit) {
      throw new ExcessiveAttemptsException();
    }
    boolean matches = super.doCredentialsMatch(token, info);
    if (matches) {
      passwordRetryCache.remove(username);
    }
    return matches;
  }

}