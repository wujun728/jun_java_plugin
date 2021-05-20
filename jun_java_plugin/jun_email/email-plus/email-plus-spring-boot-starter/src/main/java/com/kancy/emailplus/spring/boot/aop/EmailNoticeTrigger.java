package com.kancy.emailplus.spring.boot.aop;

/**
 * EmailNoticeTrigger
 *
 * @author Wujun
 * @date 2020/2/22 20:55
 */
public interface EmailNoticeTrigger {
    /**
     * 是否触发
     * @param name
     * @return
     */
    boolean isTrigger(String name);
}
