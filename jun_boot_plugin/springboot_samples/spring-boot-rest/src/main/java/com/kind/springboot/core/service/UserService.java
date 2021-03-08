/**
 * Project Name:spring-boot-rest
 * File Name:UserService.java
 * Package Name:com.kind.springboot.core.service
 * Date:2016年8月11日下午12:50:10
 * Copyright (c) 2016, http://www.mcake.com All Rights Reserved.
 */

package com.kind.springboot.core.service;

import com.kind.springboot.core.domain.UserDo;

/**
 * Function:用户服务接口. <br/>
 * Date:     2016年8月11日 下午12:50:10 <br/>
 * @author Wujun
 * @version
 * @since JDK 1.7
 * @see
 */
public interface UserService {
    /**
     * 根据用户名查询.
     * @param username
     * @return
     */
    public UserDo getByUsername(String username);

    /**
     * 根据用户id查询.
     * @param id
     * @return
     */
    public UserDo getById(Long id);

}

