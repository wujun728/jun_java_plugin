package com.designpatterns.proxy.staticproxy;

import com.designpatterns.proxy.IUserDao;
import com.designpatterns.proxy.IUserDaoImpl;
import org.junit.jupiter.api.Test;

/**
 * 静态代理
 * @author BaoZhou
 * @date 2019/1/2
 */
public class UserDaoProxyTest {
    @Test
    void test() {
        IUserDao target = new IUserDaoImpl();
        IUserDaoImplProxy  proxy = new IUserDaoImplProxy(target);
        proxy.getInfo(123);
    }
}
