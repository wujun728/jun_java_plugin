package cc.mrbird.febs.common.authentication;

import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.Strings;
import cc.mrbird.febs.common.service.RedisService;
import cc.mrbird.febs.system.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.PrincipalCollection;
import org.crazycake.shiro.RedisCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author MrBird
 */
@Slf4j
@Service
public class ShiroLogoutService {

    private RedisService redisService;

    @Autowired(required = false)
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    @Async(FebsConstant.FEBS_SHIRO_THREAD_POOL)
    public void cleanCacheFragment(PrincipalCollection principals) {
        User principal = (User) principals.getPrimaryPrincipal();
        String key = RedisCacheManager.DEFAULT_CACHE_KEY_PREFIX
                + ShiroRealm.class.getName()
                + Strings.DOT + "authenticationCache" + Strings.COLON + principal.getId();
        redisService.del(key);
        log.info("async clean up user cache fragment,cache key: [{}]", key);
    }
}