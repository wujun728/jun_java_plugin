package com.chentongwei.core.user.dao;

import com.chentongwei.core.user.entity.io.user.LoginIO;
import com.chentongwei.core.user.entity.io.user.RegistUserIO;
import com.chentongwei.core.user.entity.io.user.UserUpdateIO;
import com.chentongwei.core.user.entity.vo.user.LoginVO;
import com.chentongwei.core.user.entity.vo.user.UserVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 用户DAO
 **/
public interface IUserDAO {

    /**
     * 根据id查询用户信息
     *
     * @param userId：用户id
     * @return UserVO
     */
    UserVO getById(Long userId);

    /**
     * 根据openId查询用户信息
     *
     * @param openId：openId
     * @return UserVO
     */
    UserVO getByOpenId(String openId);

    /**
     * 注册
     *
     * @param userIO：用户信息
     */
    void regist(RegistUserIO userIO);

    /**
     * 登录
     *
     * @param loginIO：登录信息
     * @return LoginIO
     */
    LoginVO login(LoginIO loginIO);

    /**
     * 检查电子邮件是否唯一
     *
     * @param email：电子邮件
     * @return boolean
     */
    boolean getByEmail(String email);

    /**
     * 更新个人资料
     *
     * @param userUpdateIO：用户资料
     * @return
     */
    boolean update(UserUpdateIO userUpdateIO);

    /**
     * 更改邮箱
     *
     * @param userId：用户id
     * @param email：电子邮箱
     * @return
     */
    boolean updateEmail(@Param("userId") Long userId, @Param("email") String email);

    /**
     * 根据token修改密码
     *
     * @param userId：用户id
     * @param password：密码
     * @return
     */
    boolean updatePassword(@Param("userId") Long userId, @Param("password") String password);

    /**
     * 根据email修改密码
     *
     * @param email：电子邮箱
     * @param password：密码
     * @return
     */
    boolean updatePasswordByEmail(@Param("email") String email, @Param("password") String password);

    /**
     * 激活用户
     *
     * @param userId：用户id
     */
    void activeUser(Long userId);

    /**
     * 删除没有激活的用户（目前在task任务里用的，每天跑一次小程序删除那些大于两小时还没激活的用户。）
     */
    void deleteUnActiveUser();

}
