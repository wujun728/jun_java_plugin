package com.jun.plugin.util4j.net;

public interface JConnectionListener<M> {
	
    public void messageArrived(JConnection conn,M msg);
    
	public void connectionOpened(JConnection connection);

	public void connectionClosed(JConnection connection);
}
