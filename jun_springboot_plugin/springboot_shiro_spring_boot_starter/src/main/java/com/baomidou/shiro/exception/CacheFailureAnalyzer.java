package com.baomidou.shiro.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.shiro.cache.CacheException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

/**
 * 没有Realm异常处理器
 *
 * @author Wujun
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CacheFailureAnalyzer extends AbstractFailureAnalyzer<CacheException> {

  @Override
  protected FailureAnalysis analyze(Throwable rootFailure, CacheException cause) {
    return new FailureAnalysis("Cache define error.",
        "Please create a right name of refreshToken",
        cause);
  }

}
