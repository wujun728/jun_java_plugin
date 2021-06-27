/**
 * Project Name:spring-boot-rest
 * File Name:UserServiceImpl.java
 * Package Name:com.kind.springboot.core.service.impl
 * Date:2016年8月11日下午12:50:53
 * Copyright (c) 2016, http://www.mcake.com All Rights Reserved.
 */

package com.kind.springboot.core.service.impl;

import com.kind.springboot.core.domain.UserDo;
import com.kind.springboot.core.repository.UserRepository;
import com.kind.springboot.core.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Function:用户服务实现. <br/>
 * Date: 2016年8月11日 下午12:50:53 <br/>
 *
 * @author Wujun
 * @version
 * @since JDK 1.7
 * @see
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository userRepository;

    @Override
    public UserDo getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public UserDo getById(Long id) {
        return userRepository.findOne(id);
    }

}
