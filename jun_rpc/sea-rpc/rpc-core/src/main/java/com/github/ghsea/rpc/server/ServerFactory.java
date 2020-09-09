package com.github.ghsea.rpc.server;

public class ServerFactory {

	private static Server server = new NettyServer();

	public static Server getInstance() {
		return server;
	}
}
