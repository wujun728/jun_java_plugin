package org.typroject.tyboot.core.auth.face.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.typroject.tyboot.component.cache.Redis;
import org.typroject.tyboot.component.cache.enumeration.CacheType;
import org.typroject.tyboot.core.auth.exception.AuthException;
import org.typroject.tyboot.core.auth.exception.ConflictException;
import org.typroject.tyboot.core.auth.face.model.LoginHistoryModel;
import org.typroject.tyboot.core.auth.face.model.LoginInfoModel;
import org.typroject.tyboot.core.auth.face.model.SsoSessionsModel;
import org.typroject.tyboot.core.auth.face.orm.dao.SsoSessionsMapper;
import org.typroject.tyboot.core.auth.face.orm.entity.SsoSessions;
import org.typroject.tyboot.core.foundation.exception.BaseException;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;
import org.typroject.tyboot.core.rdbms.service.BaseService;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 登录SESSION 服务实现类
 * </p>
 *
 * @author Wujun
 * @since 2017-07-06
 */
@Service
public class SsoSessionsService extends BaseService<SsoSessionsModel,SsoSessions,SsoSessionsMapper> {


    public static final String SESSION = "SESSION";
    public static final String SESSION_TOKEN = "SESSION_TOKEN";
    public static final String SESSION_LOGINID = "SESSION_LOGINID";

    public static  Long DEFAULT_SESSION_EXPIRATION = 2592000L;


    @Autowired
    private RedisTemplate redisTemplate;

    public static void setDefaultExpiration(Long expiration)
    {
        DEFAULT_SESSION_EXPIRATION = expiration;
    }

    public static String sessionCacheKeyWithToken(String token,String actionByProduct)
    {
        return Redis.genKey(CacheType.ERASABLE.name(),SESSION_TOKEN,actionByProduct,token);
    }

    public static String sessionCacheKeyWithLoginId(String loginId,String actionByProduct)
    {
        return Redis.genKey(CacheType.ERASABLE.name(),SESSION,actionByProduct,loginId);
    }
    public static String loginIdCacheWithToken(String token)
    {
        return Redis.genKey(CacheType.ERASABLE.name(),SESSION_LOGINID,token);
    }

    /**
     * 刷新session
     * @param token
     * @param actionByProduct
     * @return
     */
    public  SsoSessionsModel refreshSession(String token,String actionByProduct){

        SsoSessionsModel sessionsModel =
                (SsoSessionsModel)this.redisTemplate.opsForValue().get(sessionCacheKeyWithToken(token,actionByProduct));
        if(!ValidationUtil.isEmpty(sessionsModel))
        {
            redisTemplate.expire(sessionCacheKeyWithToken(token,actionByProduct),DEFAULT_SESSION_EXPIRATION, TimeUnit.SECONDS);
            redisTemplate.expire(sessionCacheKeyWithLoginId(sessionsModel.getLoginId(),actionByProduct),DEFAULT_SESSION_EXPIRATION, TimeUnit.SECONDS);

            sessionsModel.setSessionExpiration(SsoSessionsService.DEFAULT_SESSION_EXPIRATION);
        }else{
            String loginId =  (String)redisTemplate.opsForValue().get(loginIdCacheWithToken(token));
            if(!ValidationUtil.isEmpty(loginId))
            {
               boolean hasOtherToken =  Redis.getRedisTemplate().hasKey(sessionCacheKeyWithLoginId(loginId,actionByProduct));
               if(hasOtherToken)
                   throw new ConflictException("当前账号已经在其他设备登录.");
            }
            throw new AuthException("登录信息失效，请重新登录");
        }
        return sessionsModel;
    }



    @Transactional(rollbackFor = {Exception.class, BaseException.class})
    public SsoSessionsModel createSession(SsoSessionsModel sessionsModel)
    {
        this.removeSession(sessionsModel.getActionByProduct(),sessionsModel.getLoginId());
        this.redisTemplate.opsForValue().set(sessionCacheKeyWithToken(sessionsModel.getToken(),sessionsModel.getActionByProduct()),sessionsModel);
        this.redisTemplate.opsForValue().set(sessionCacheKeyWithLoginId(sessionsModel.getLoginId(),sessionsModel.getActionByProduct()),sessionsModel);
        this.redisTemplate.opsForValue().set(loginIdCacheWithToken(sessionsModel.getToken()),sessionsModel.getLoginId());

        redisTemplate.expire(sessionCacheKeyWithToken(sessionsModel.getToken(),sessionsModel.getActionByProduct()),DEFAULT_SESSION_EXPIRATION, TimeUnit.SECONDS);
        redisTemplate.expire(sessionCacheKeyWithLoginId(sessionsModel.getLoginId(),sessionsModel.getActionByProduct()),DEFAULT_SESSION_EXPIRATION, TimeUnit.SECONDS);
        redisTemplate.expire(loginIdCacheWithToken(sessionsModel.getToken()),DEFAULT_SESSION_EXPIRATION, TimeUnit.SECONDS);


        return sessionsModel;
    }



    public void removeSession(String  actionByProduct ,String loginId)
    {
        SsoSessionsModel sessionsModel = (SsoSessionsModel)this.redisTemplate.opsForValue().get(sessionCacheKeyWithLoginId(loginId,actionByProduct));
        if(!ValidationUtil.isEmpty(sessionsModel))
        {
            this.redisTemplate.delete(sessionCacheKeyWithLoginId(loginId,actionByProduct));
            this.redisTemplate.delete(sessionCacheKeyWithToken(sessionsModel.getToken(),actionByProduct));
            //this.redisTemplate.delete(loginIdCacheWithToken(sessionsModel.getToken()));
        }

    }

    public void removeAllSession(String[] products ,String loginId)
    {
        for(String product : products)
        {
            this.removeSession(product,loginId);
        }
    }


    public  SsoSessionsModel queryByToken(String  actionByProduct ,String token)
    {

        SsoSessionsModel sessionsModel = (SsoSessionsModel)this.redisTemplate.opsForValue().get(sessionCacheKeyWithToken(token,actionByProduct));
        return sessionsModel;
    }

    public  SsoSessionsModel queryByLoginId(String  actionByProduct ,String loginId)
    {

        SsoSessionsModel sessionsModel = (SsoSessionsModel)this.redisTemplate.opsForValue().get(sessionCacheKeyWithLoginId(loginId,actionByProduct));
        return sessionsModel;
    }





}
