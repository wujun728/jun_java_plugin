package com.designpatterns.proxy.dynamic.cglib;

import com.designpatterns.proxy.IUserDao;
import com.designpatterns.proxy.IUserDaoImpl;
import org.junit.jupiter.api.Test;

/**
 * @author BaoZhou
 * @date 2019/1/2
 */
public class CglibTest {
    @Test
    void test() {
        IUserDao target = new IUserDaoImpl();
        IUserDaoCglibProxy cglib = new IUserDaoCglibProxy();
        IUserDaoImpl instance = (IUserDaoImpl) cglib.getInstance(target);
        instance.getUserInfo(123);
    }
}
