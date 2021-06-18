package com.jun.plugin.fastnetty.server;

import java.util.Set;

/**
 * 服务端管理器接口
 * @author peiyu
 */
public interface ServerManager {

    void addServer(NettyServer server);

    void setServers(Set<NettyServer> servers);

    void startServers(int tcpPort, int udpPort) throws Exception;

    void startServers() throws Exception;

    void stopServers() throws Exception;
}
