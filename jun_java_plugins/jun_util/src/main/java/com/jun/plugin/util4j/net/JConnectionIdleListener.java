package com.jun.plugin.util4j.net;

/**
 * 带心跳操作的监听器
 * @author Administrator
 * @param <M>
 */
public interface JConnectionIdleListener<M> extends JConnectionListener<M>{
	
	/**
	 * 读超时时间
	 * @return
	 */
	public long getReaderIdleTimeMills();
	/**
	 * 写超时时间
	 * @return
	 */
    public long getWriterIdleTimeMills();
    /**
     * 读写超时时间
     * @return
     */
    public long getAllIdleTimeMills();
    
    /**
     * 读写空闲超时事件
     * @param connection
     */
    public void event_AllIdleTimeOut(JConnection connection);
    /**
     * 读空闲超时
     * @param connection
     */
    public void event_ReadIdleTimeOut(JConnection connection);
    /**
     * 写空闲超时
     * @param connection
     */
    public void event_WriteIdleTimeOut(JConnection connection);
    
    public void messageArrived(JConnection conn,M msg);
    
	public void connectionOpened(JConnection connection);

	public void connectionClosed(JConnection connection);
}
