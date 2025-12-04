/**
 * @filename:UserServiceImpl 2019年5月20日
 * @project springboot-mybatis  V1.0
 * Copyright(c) 2018 flying-cattle Co. Ltd. 
 * All right reserved. 
 */
package com.flying.cattle.service.impl;

import com.flying.cattle.entity.User;
import com.flying.cattle.dao.UserDao;
import com.flying.cattle.service.UserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**   
 * Copyright: Copyright (c) 2019 
 * 
 * <p>说明： 用户服务实现层</P>
 * @version: V1.0
 * @author: flying-cattle
 * 
 * Modification History:
 * Date         	Author          Version          Description
 *---------------------------------------------------------------*
 * 2019年5月20日      flying-cattle    V1.0           initialize
 */
@Service
public class UserServiceImpl  extends ServiceImpl<UserDao, User> implements UserService  {
	
}