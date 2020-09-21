package com.github.sd4324530.fastrpc.server.handler;

import com.github.sd4324530.fastrpc.core.channel.IChannel;

/**
 * @author peiyu
 */
public interface IRpcServerHandler {

    void handler(IChannel channel);
}
