package com.jun.plugin.fastnetty.server.impl;

import java.util.HashSet;
import java.util.Set;

import com.jun.plugin.fastnetty.server.NettyServer;
import com.jun.plugin.fastnetty.server.ServerManager;

/**
 * @author Wujun
 */
public final class ServerManagerImpl implements ServerManager {

    private Set<NettyServer> servers;

    public ServerManagerImpl() {
        this.servers = new HashSet<>();
    }

    @Override
    public void addServer(NettyServer server) {
        this.servers.add(server);
    }

    @Override
    public void setServers(Set<NettyServer> servers) {
        this.servers.addAll(servers);
    }

    @Override
    public void startServers(int tcpPort, int udpPort) throws Exception {
        if (!this.servers.isEmpty()) {
            for (NettyServer server : this.servers) {
                if (server instanceof NettyTCPServer) {
                    server.startServer(tcpPort);
                } else if (server instanceof NettyUDPServer) {
                    server.startServer(udpPort);
                }
            }
        }
    }

    @Override
    public void startServers() throws Exception {
        if (!this.servers.isEmpty()) {
            for (NettyServer server : this.servers) {
                server.startServer();
            }
        }
    }

    @Override
    public void stopServers() throws Exception {
        if (!this.servers.isEmpty()) {
            for (NettyServer server : this.servers) {
                server.stopServer();
            }
        }
    }
}
