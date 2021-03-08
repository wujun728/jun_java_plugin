package org.typroject.tyboot.core.restful.limit.impl;

import org.springframework.web.method.HandlerMethod;
import org.typroject.tyboot.component.cache.Redis;
import org.typroject.tyboot.component.cache.enumeration.CacheType;
import org.typroject.tyboot.core.auth.face.model.SsoSessionsModel;
import org.typroject.tyboot.core.foundation.context.RequestContext;
import org.typroject.tyboot.core.restful.limit.Frequency;
import org.typroject.tyboot.core.restful.limit.LimitStrategy;
import org.typroject.tyboot.core.restful.limit.Strategy;

import java.util.concurrent.TimeUnit;

/**
 * 使用IP限制用户请求频次
 * 在刷新session之前就执行限制校验
 *
 *
 */
public class IpRestrictiveStrategy implements LimitStrategy {


    //每分钟 每个IP 最多发起100个请求
   private   Frequency frequency = new Frequency(TimeUnit.MINUTES,1L,100L);;



   public IpRestrictiveStrategy()
   {

   }

    public IpRestrictiveStrategy(Frequency frequency)
    {
        this.frequency = frequency;
    }

    @Override
    public String  incrementKey(HandlerMethod handlerMethod) {

        return Redis.genKey(
                CacheType.ERASABLE.name(),
                CACHE_KEY_PREFIX,
                Strategy.IP.name(),
                RequestContext.getRequestIP());
    }

    @Override
    public Frequency getFrequency() {
        return frequency;
    }


    @Override
    public boolean isEnable(HandlerMethod handlerMethod) {
        return true;
    }

    @Override
    public boolean afterTokenAuth() {
        return false;
    }
}
