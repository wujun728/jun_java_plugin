package cn.antcore.security.event;

import org.springframework.context.ApplicationEvent;

/**
 * 创建Session
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/24</p>
 **/
public class CreateSessionEvent extends ApplicationEvent {

    private String oldSessionId;

    private String sessionId;

    public CreateSessionEvent(Object source, String oldSessionId, String sessionId) {
        super(source);
        this.oldSessionId = oldSessionId;
        this.sessionId = sessionId;
    }

    public String getOldSessionId() {
        return oldSessionId;
    }

    public String getSessionId() {
        return sessionId;
    }
}
