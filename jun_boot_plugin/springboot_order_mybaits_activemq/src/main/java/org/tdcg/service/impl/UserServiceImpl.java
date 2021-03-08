package org.tdcg.service.impl;

import com.baomidou.framework.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.tdcg.entity.User;
import org.tdcg.mapper.UserMapper;
import org.tdcg.service.UserService;

/**
 *
 * User 表数据服务层接口实现类
 *
 */
@Service
public class UserServiceImpl extends CommonServiceImpl<UserMapper, User> implements UserService {


}