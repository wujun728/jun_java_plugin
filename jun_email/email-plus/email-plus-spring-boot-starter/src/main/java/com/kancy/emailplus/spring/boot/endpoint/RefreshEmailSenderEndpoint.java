package com.kancy.emailplus.spring.boot.endpoint;

import com.kancy.emailplus.spring.boot.listener.event.RefreshEmailSenderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * RefreshEmailSenderEndpoint
 *
 * @author kancy
 * @date 2020/2/24 19:16
 */
@Endpoint(id = "refreshEmailSender")
public class RefreshEmailSenderEndpoint {

    @Autowired
    private ApplicationContext applicationContext;

    @ReadOperation
    public Object refresh(){
        Map<String, Object> map = new HashMap<>();
        try {
            applicationContext.publishEvent(new RefreshEmailSenderEvent());
            map.put("success","true");
        } catch (Exception e) {
            map.put("success","false");
            map.put("message",e.getMessage());
        }
        return map;
    }

}
