package cn.antcore.security.event;

import org.springframework.context.ApplicationEvent;

/**
 * 更新Session
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/24</p>
 **/
public class UpdateSessionEvent extends ApplicationEvent {

    private String sessionId;

    public UpdateSessionEvent(Object source, String sessionId) {
        super(source);
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }
}
