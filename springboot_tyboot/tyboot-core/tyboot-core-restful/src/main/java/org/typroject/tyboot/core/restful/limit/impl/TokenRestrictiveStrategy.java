package org.typroject.tyboot.core.restful.limit.impl;

import org.springframework.web.method.HandlerMethod;
import org.typroject.tyboot.component.cache.Redis;
import org.typroject.tyboot.component.cache.enumeration.CacheType;
import org.typroject.tyboot.core.auth.face.model.SsoSessionsModel;
import org.typroject.tyboot.core.foundation.context.RequestContext;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;
import org.typroject.tyboot.core.restful.limit.Frequency;
import org.typroject.tyboot.core.restful.limit.LimitStrategy;
import org.typroject.tyboot.core.restful.limit.Strategy;

import java.util.concurrent.TimeUnit;


/**
 * 使用请求token来限制用户的请求。
 * 在刷新session之后执行限制校验
 */
public class TokenRestrictiveStrategy implements LimitStrategy {


    //默认每分钟 每个token 最多发起50个请求
   private   Frequency frequency = new Frequency(TimeUnit.MINUTES,1L,100L);

   public TokenRestrictiveStrategy(Frequency frequency)
   {
            this.frequency = frequency;
   }

   public TokenRestrictiveStrategy(){

   }


    @Override
    public String  incrementKey(HandlerMethod handlerMethod)  {

        return Redis.genKey(
                CacheType.ERASABLE.name(),
                CACHE_KEY_PREFIX,
                Strategy.TOKEN.name(),
                RequestContext.getToken());
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
        return true;
    }
}
