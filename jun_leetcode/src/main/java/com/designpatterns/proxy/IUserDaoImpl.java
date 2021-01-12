package com.designpatterns.proxy;

/**
 * @author BaoZhou
 * @date 2019/1/2
 */
public class IUserDaoImpl implements IUserDao {
    @Override
    public String getUserInfo(int userId) {
        System.out.println("获取用户信息");
        return "用户信息";
    }
}
