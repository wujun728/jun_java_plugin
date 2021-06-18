package com.kancy.emailplus.spring.boot.listener.event;

import org.springframework.context.ApplicationEvent;

import java.util.Set;

/**
 * RefreshEmailSenderEvent
 *
 * @author Wujun
 * @date 2020/2/21 21:44
 */
public class RefreshEmailSenderEvent extends ApplicationEvent {
    private Set<String> changeKeys;

    public RefreshEmailSenderEvent() {
        super("");
    }
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public RefreshEmailSenderEvent(Object source) {
        super(source);
    }

    public RefreshEmailSenderEvent(Object source, Set<String> changeKeys) {
        super(source);
        this.changeKeys = changeKeys;
    }

    public Set<String> getChangeKeys() {
        return changeKeys;
    }
}
