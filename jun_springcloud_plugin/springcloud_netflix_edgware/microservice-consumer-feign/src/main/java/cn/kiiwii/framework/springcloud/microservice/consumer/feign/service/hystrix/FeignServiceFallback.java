package cn.kiiwii.framework.springcloud.microservice.consumer.feign.service.hystrix;

import cn.kiiwii.framework.springcloud.microservice.consumer.feign.model.User;
import cn.kiiwii.framework.springcloud.microservice.consumer.feign.service.UserFeignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by zhong on 2017/4/18.
 */
@Component
public class FeignServiceFallback implements UserFeignService {

    private static final Logger logger = LoggerFactory.getLogger(FeignServiceFallback.class);
    /**
     * hystrix fallback⽅法
     * @param id id
     * @return 默认的⽤户
     */
    @Override
    public User findByIdFeign(Long id) {
        FeignServiceFallback.logger.info("异常发⽣，进⼊fallback⽅法，接收的参数：id = {}", id);
        User user = new User();
        user.setId(-1L);
        user.setUsername("default username");
        user.setAge(0);
        return user;
    }
}
