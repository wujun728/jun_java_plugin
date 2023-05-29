package com.jun.plugin.util4j.net;

import java.util.Set;

/**
 * 网络服务器
 * @author Administrator
 */
public interface JNetServer {

	public boolean start();
	public void stop();
	public JConnection getConnection(long id);
	public Set<JConnection> getConnections();
	public String getName();
	public void setName(String name);
	public void broadCast(Object message);
	public boolean isActive();
	public int getConnectionCount();
}
