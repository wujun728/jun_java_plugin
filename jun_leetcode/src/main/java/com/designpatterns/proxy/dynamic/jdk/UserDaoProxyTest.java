package com.designpatterns.proxy.dynamic.jdk;

import com.designpatterns.proxy.IUserDao;
import com.designpatterns.proxy.IUserDaoImpl;
import org.junit.jupiter.api.Test;

/**
 * 动态代理
 * @author BaoZhou
 * @date 2019/1/2
 */
public class UserDaoProxyTest {
    @Test
    void test() {
        IUserDao target = new IUserDaoImpl();
        IUserDao proxy = (IUserDao) new IUserDaoDynamicProxy(target).bind();
        proxy.getUserInfo(234);
    }
}
