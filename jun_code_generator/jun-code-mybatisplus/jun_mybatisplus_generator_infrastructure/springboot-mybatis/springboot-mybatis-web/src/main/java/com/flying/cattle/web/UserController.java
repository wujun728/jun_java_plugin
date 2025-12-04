/**
 * @filename:UserController 2019年5月20日
 * @project springboot-mybatis  V1.0
 * Copyright(c) 2020 flying-cattle Co. Ltd. 
 * All right reserved. 
 */
package com.flying.cattle.web;

import com.flying.cattle.aid.AbstractController;
import com.flying.cattle.entity.User;
import com.flying.cattle.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 用户API接口层</P>
 * @version: V1.0
 * @author: flying-cattle
 *
 */
@Api(description = "用户",value="用户" )
@RestController
@RequestMapping("/user")
public class UserController extends AbstractController<UserService,User>{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
}