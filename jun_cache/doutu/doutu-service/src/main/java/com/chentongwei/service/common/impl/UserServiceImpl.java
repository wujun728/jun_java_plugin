package com.chentongwei.service.common.impl;

import com.chentongwei.common.constant.ResponseEnum;
import com.chentongwei.common.handler.CommonExceptionHandler;
import com.chentongwei.dao.common.IUserDAO;
import com.chentongwei.entity.common.io.LoginIO;
import com.chentongwei.entity.common.vo.UserVO;
import com.chentongwei.service.common.IUserService;
import com.chentongwei.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户业务接口实现类
 *
 * @author TongWei.Chen 2017-06-10 15:17
 **/
@Service
public class UserServiceImpl implements IUserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private IUserDAO userDAO;

    @Override
    public UserVO login(LoginIO loginIO) {
        loginIO.setPassword(MD5Util.encode(loginIO.getPassword()));
        UserVO userVO = userDAO.login(loginIO);
        //检查用户名密码是否匹配
        CommonExceptionHandler.nullCheck(userVO, ResponseEnum.LOGIN_ERROR);
        //检查用户是否被禁用
        CommonExceptionHandler.userDisabledCheck(userVO.getStatus());
        //检查是否是管理员，非管理员不能登录后台管理系统
        CommonExceptionHandler.isAdminCheck(userVO.isAdmin());
        LOG.info(loginIO.getLoginName(), "登陆成功");
        return userVO;
    }
}
