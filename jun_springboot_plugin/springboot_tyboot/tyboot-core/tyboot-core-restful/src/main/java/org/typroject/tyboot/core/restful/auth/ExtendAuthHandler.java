package org.typroject.tyboot.core.restful.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.typroject.tyboot.core.auth.face.model.SsoSessionsModel;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;
import org.typroject.tyboot.core.restful.auth.impl.UserTypeAuthHandler;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExtendAuthHandler implements InitializingBean {
    private static final Logger logger = LogManager.getLogger(ExtendAuthHandler.class);

    //这里边的验证规则，每个请求都会执行，请慎重所添加的验证器数量，和执行时间，执行顺序为添加的顺序
    private static List<AuthHandler> authHandlers                       = new ArrayList<>();
    private static List<AuthWithSessionHandler> authWithSessionHandlers = new ArrayList<>();

    private static final long duration = 10L;//每种验证规则的执行时间不能超过10毫秒


    @Autowired
    private UserTypeAuthHandler userTypeAuthHandler;


    @Override
    public void afterPropertiesSet() throws Exception {

        authWithSessionHandlers.add(userTypeAuthHandler);
    }

    public static void doAuth(SsoSessionsModel ssoSessionsModel, HandlerMethod handlerMethod, String token, String appKey, String product) throws Exception {


        //扩展的验证规则
        long time = System.currentTimeMillis();
        if(!ValidationUtil.isEmpty(authWithSessionHandlers))
            for(AuthWithSessionHandler authHandler :authWithSessionHandlers)
                authHandler.doAuth(ssoSessionsModel,handlerMethod,token,appKey,product);

        long currentDuration = System.currentTimeMillis() - time;
        if(currentDuration > duration)
            logger.warn("扩展验证规则执行时间过长，超过了"+duration+"毫秒.当前执行时间为："+currentDuration);
    }


    public static Boolean doAuth(HandlerMethod handlerMethod, String token, String appKey, String product) {


        //扩展的验证规则
        long time = System.currentTimeMillis();
        boolean flag = false;
        if(!ValidationUtil.isEmpty(authHandlers))
            for(AuthHandler authHandler :authHandlers)
                flag = flag || authHandler.doAuth(handlerMethod,token,appKey,product);

        long currentDuration = System.currentTimeMillis() - time;
        if(currentDuration > duration)
            logger.warn("扩展验证规则执行时间过长，超过了"+duration+"毫秒.当前执行时间为："+currentDuration);

        //如果返回真，则跳过剩下所有校验。
        return flag;
    }


    public static void addAuthHandler(AuthHandler authHandler)
    {
        authHandlers.add(authHandler);
    }

    public static void addAuthWithSessionHandler(AuthWithSessionHandler authWithSessionHandler)
    {
        authWithSessionHandlers.add(authWithSessionHandler);
    }

}
