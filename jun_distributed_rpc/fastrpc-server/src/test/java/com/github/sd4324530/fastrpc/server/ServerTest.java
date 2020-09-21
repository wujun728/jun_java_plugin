package com.github.sd4324530.fastrpc.server;

/**
 * @author peiyu
 */
public class ServerTest {

    public static void main(String[] args) throws Exception {
        new FastRpcServer()
                .threadSize(20)
                .register("test", new TestService())
                .bind(4567)
                .start();
    }
}
