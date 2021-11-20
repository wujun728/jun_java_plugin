package com.jun.base.io.netty.rpc.server;

import com.jun.base.io.netty.rpc.common.ExpRequest;
import com.jun.base.io.netty.rpc.common.ExpRequestHandler;
import com.jun.base.io.netty.rpc.common.FibRequestHandler;

public class DemoServer {
    public static void main(String[] args) {
        RPCServer server = new RPCServer("localhost", 8888, 2, 16);
        server.service("fib", Integer.class, new FibRequestHandler())
                .service("exp", ExpRequest.class, new ExpRequestHandler());
        server.start();

    }
}
