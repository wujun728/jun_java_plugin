package com.jun.plugin.fastrpc.server;

import com.jun.plugin.fastrpc.server.FastRpcServer;

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
