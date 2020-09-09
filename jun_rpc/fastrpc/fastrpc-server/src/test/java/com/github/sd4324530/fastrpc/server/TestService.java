package com.github.sd4324530.fastrpc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author peiyu
 */
public class TestService implements ITestService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public String say(String what) {
        String result = "say " + what;
        log.debug(result);
        return result;
    }

    @Override
    public String name() {
        log.debug("call name");
        return "call name";
    }

    @Override
    public void ok(String ok) {
        log.debug("call ok");
        log.debug("param:{}", ok);
    }

    @Override
    public void none() {
        log.debug("call none");
    }

    @Override
    public User doUser(User user) {
        log.debug("收到user:" + user);
        user.setAge(user.getAge() - 1);
        user.setName("hello " + user.getName());
        user.setSex(!user.isSex());
        return user;
    }
}
