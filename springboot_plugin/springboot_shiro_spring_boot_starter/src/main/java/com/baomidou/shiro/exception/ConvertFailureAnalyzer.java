package com.baomidou.shiro.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

/**
 * realm或filter转换异常处理器
 *
 * @author Wujun
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ConvertFailureAnalyzer extends AbstractFailureAnalyzer<ConvertException> {

  @Override
  protected FailureAnalysis analyze(Throwable rootFailure, ConvertException cause) {
    return new FailureAnalysis("Filter or Realm convert fail",
        "Check your yml config. Be sure the right classpath",
        cause);
  }

}
