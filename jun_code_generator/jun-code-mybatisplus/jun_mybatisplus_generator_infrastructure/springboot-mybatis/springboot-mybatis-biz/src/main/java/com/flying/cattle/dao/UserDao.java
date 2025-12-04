/**
 * @filename:UserDao 2019年5月20日
 * @project springboot-mybatis  V1.0
 * Copyright(c) 2018 flying-cattle Co. Ltd. 
 * All right reserved. 
 */
package com.flying.cattle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.flying.cattle.entity.User;

/**   
 * Copyright: Copyright (c) 2019 
 * 
 * <p>说明： 用户数据访问层</p>
 * @version: V1.0
 * @author: flying-cattle
 * 
 * Modification History:
 * Date         	Author          Version          Description
 *---------------------------------------------------------------*
 * 2019年5月20日      flying-cattle    V1.0         initialize
 */
@Mapper
public interface UserDao extends BaseMapper<User> {
	
}
