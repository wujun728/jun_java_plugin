package com.chentongwei.dao.common;

import com.chentongwei.entity.common.io.LoginIO;
import com.chentongwei.entity.common.vo.UserVO;

/**
 * 用户DAO
 *
 * @author TongWei.Chen 2017-06-10 15:15
 **/
public interface IUserDAO {

    /**
     * 用户登录
     *
     * @param loginIO：登录信息
     * @return
     */
    UserVO login(LoginIO loginIO);
}
