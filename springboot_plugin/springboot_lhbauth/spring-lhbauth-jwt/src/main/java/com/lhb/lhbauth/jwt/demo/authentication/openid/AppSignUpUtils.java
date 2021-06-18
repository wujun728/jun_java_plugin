package com.lhb.lhbauth.jwt.demo.authentication.openid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.web.context.request.WebRequest;

import java.util.concurrent.TimeUnit;

/**
 * @author Wujun
 * @description
 * @date 2018/12/18 0018 10:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppSignUpUtils {

    private RedisTemplate<Object, Object> redisTemplate;


    private UsersConnectionRepository repository;


    private ConnectionFactoryLocator connectionFactoryLocator;

    public void saveConnectionData(WebRequest request, ConnectionData connectionData){
        redisTemplate.opsForValue().set(getKey(request), connectionData,10, TimeUnit.MINUTES);
    }



    public void doPostSignUp(WebRequest request, String userId){

        String key = getKey(request);
        if(!redisTemplate.hasKey(key)){
            throw new RuntimeException("无法找到缓存的第三方用户信息");
        }

        ConnectionData connectionData = (ConnectionData) redisTemplate.opsForValue().get(key);

        Connection<?> connection = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId())
                .createConnection(connectionData);
        repository.createConnectionRepository(userId).addConnection(connection);

        redisTemplate.delete(key);
    }

    private String getKey(WebRequest request) {


        String de = request.getHeader("de");

        if(StringUtils.isBlank(de)){
            throw new RuntimeException("设备de参数不能为空");
        }
        return "system.social.connect"+de;
    }


}
