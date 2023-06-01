package cn.antcore.security.session.strategy;

import cn.antcore.security.event.UpdateSessionEvent;
import cn.antcore.security.helper.ContextUtils;
import cn.antcore.security.session.SessionIdStrategy;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * SessionId创建策略 - Header
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/24</p>
 **/
public class HeaderSessionIdStrategy implements SessionIdStrategy {

    @Override
    public String getSessionName() {
        return "X-Auth-Token";
    }

    @Override
    public String getSessionId(HttpServletRequest request) {
        String sessionId = null;
        if (request != null) {
            sessionId = request.getHeader(getSessionName());
            if (!StringUtils.isEmpty(sessionId)) {
                ContextUtils.publishEvent(new UpdateSessionEvent(this, sessionId));
            }
        }
        return sessionId;
    }

    @Override
    public String createSessionId(HttpServletRequest request) {
        String sessionId = UUID.randomUUID().toString()
                .replaceAll("-", "");
        return sessionId;
    }
}
