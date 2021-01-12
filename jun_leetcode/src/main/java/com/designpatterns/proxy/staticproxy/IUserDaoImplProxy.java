package com.designpatterns.proxy.staticproxy;

import com.designpatterns.proxy.IUserDao;

/**
 * @author BaoZhou
 * @date 2019/1/2
 */
public class IUserDaoImplProxy {
    IUserDao target;

    public IUserDaoImplProxy(IUserDao userDao) {
        target = userDao;
    }

    public void getInfo(int userId) {
        beforeMethod(userId);
        String result = target.getUserInfo(userId);
        afterMethod(result);
    }

    private int beforeMethod(int result) {
        System.out.println("before..." + result);
        return 1;
    }

    private void afterMethod(String result) {
        System.out.println("after..." + result);
    }
}
