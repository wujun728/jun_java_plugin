package com.github.ghsea.rpc.client;

import java.util.HashMap;
import java.util.Map;

import com.github.ghsea.rpc.common.modle.NodeId;

public class NettyClientFactory {

	static class NettyClientFactoryHolder {
		final static NettyClientFactory instance = new NettyClientFactory();
	}

	private Map<NodeId, NettyClient> nodeId2Client = new HashMap<NodeId, NettyClient>();

	private NettyClientFactory() {

	}

	public static NettyClientFactory getInstance() {
		return NettyClientFactoryHolder.instance;
	}

	public NettyClient getNettyClient(NodeId serviceNode) {
		NettyClient client = nodeId2Client.get(serviceNode);
		if (client == null) {
			synchronized (NettyClientFactory.class) {
				if (client == null) {
					client = new NettyClient(serviceNode);
					nodeId2Client.put(serviceNode, client);
				}
			}
		}

		return client;
	}

}
