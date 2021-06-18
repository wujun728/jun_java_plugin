package com.jun.plugin.fastnetty.server;

import java.net.InetSocketAddress;

/**
 * 基于Netty通讯服务端接口
 * @author peiyu
 */
public interface NettyServer {

    enum ServerType {
        TCP,UDP
    }

    ServerType getServerType();

    void startServer() throws Exception;

    void startServer(int port) throws Exception;

    void startServer(InetSocketAddress socketAddress) throws Exception;

    void stopServer() throws Exception;

    InetSocketAddress getSocketAddress();

    void setSocketAddress(InetSocketAddress socketAddress);
}
