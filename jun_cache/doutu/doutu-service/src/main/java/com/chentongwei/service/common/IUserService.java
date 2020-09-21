package com.chentongwei.service.common;

import com.chentongwei.entity.common.io.LoginIO;
import com.chentongwei.entity.common.vo.UserVO;

/**
 * 用户业务接口
 *
 * @author TongWei.Chen 2017-06-10 15:17
 **/
public interface IUserService {

    /**
     * 用户登录
     *
     * @param loginIO：登录信息
     * @return
     */
    UserVO login(LoginIO loginIO);
}
