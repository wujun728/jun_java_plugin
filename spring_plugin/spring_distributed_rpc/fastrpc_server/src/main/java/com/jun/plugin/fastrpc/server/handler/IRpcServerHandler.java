package com.jun.plugin.fastrpc.server.handler;

import com.jun.plugin.fastrpc.core.channel.IChannel;

/**
 * @author peiyu
 */
public interface IRpcServerHandler {

    void handler(IChannel channel);
}
