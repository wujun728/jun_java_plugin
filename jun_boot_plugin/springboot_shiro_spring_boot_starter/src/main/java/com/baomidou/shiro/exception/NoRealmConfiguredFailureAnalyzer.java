package com.baomidou.shiro.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

/**
 * 没有Realm异常处理器
 *
 * @author Wujun
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NoRealmConfiguredFailureAnalyzer extends
    AbstractFailureAnalyzer<NoRealmConfiguredException> {

  @Override
  protected FailureAnalysis analyze(Throwable rootFailure, NoRealmConfiguredException cause) {
    return new FailureAnalysis("No bean of type 'org.apache.shiro.realm.Realm' found.",
        "Please create a bean of type 'Realm' or "
            + "config yml shiro.realms or"
            + "add a shiro.ini in the root classpath (src/main/resources/shiro.ini) "
            + "or in the META-INF folder (src/main/resources/META-INF/shiro.ini).",
        cause);
  }

}
