package com.jun.plugin.fastrpc.server;

/**
 * @author peiyu
 */
public interface ITestService {

    String say(String what);

    String name();

    void ok(String ok);

    void none();

    User doUser(User user);
}
