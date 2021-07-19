package com.buxiaoxia.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by xw on 2017/9/13.
 * 2017-09-13 19:11
 */
@Component
@FeignClient(value = "avatar", url = "127.0.0.1:8081", fallback = AvatarClientFallback.class)
public interface AvatarClient {

    @RequestMapping(value = "/api/v1.0/avatars", method = RequestMethod.GET)
    String getAvatar(@RequestParam("id") Long id);



}

@Component
class AvatarClientFallback implements AvatarClient {

    @Override
    public String getAvatar(Long id) {
        return null;
    }
}