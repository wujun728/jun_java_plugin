package com.jun.plugin.util4j.net.nettyImpl.listener;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.util4j.net.JConnection;
import com.jun.plugin.util4j.net.JConnectionIdleListener;

/**
 * 具有心跳监测机制的链路监听器
 * 如果你想使用心跳:
 * {@code public void connectionOpened(NetConnection connection) {
		//设置心跳配置
		HeartConfig heart=new HeartConfig();
		connection.setAttribute(IdleConnectionKey.HeartConfig, heart);
		setHeartEnable(true);
 * }
 * @author Administrator
 */
public abstract class HeartAbleConnectionListener<T> implements JConnectionIdleListener<T>{

	protected final Logger _log = LoggerFactory.getLogger(getClass());
	
	/**
	 * 心跳验证间隔(仅当空闲时)
	 */
	public final static long HeartIntervalMills=TimeUnit.SECONDS.toMillis(5);
	
	/**
	 * 心跳开关
	 */
	private boolean heartEnable;

	public boolean isHeartEnable() {
		return heartEnable;
	}

	public void setHeartEnable(boolean heartEnable) {
		this.heartEnable = heartEnable;
	}
	
	/**
	 * 链路心跳配置
	 * @param connection
	 * @param config
	 */
	public void heartConfig (JConnection connection,HeartConfig config)
	{
		Objects.requireNonNull(config);
		connection.setAttribute(IdleConnectionKey.HeartConfig, config);
	}

	/**
	 * 心跳配置
	 * @author Administrator
	 */
	public static class HeartConfig{
		public static final int DefaultCloseMaxSeq=2;//最大发送序号(最多发送几次心跳验证)
		private int seq;//发送序号
		private int closeMaxSeq=DefaultCloseMaxSeq;//最大关闭序号
		public int getSeq() {
			return seq;
		}
		public void setSeq(int seq) {
			this.seq = seq;
		}
		public int getCloseMaxSeq() {
			return closeMaxSeq;
		}
		public void setCloseMaxSeq(int closeMaxSeq) {
			this.closeMaxSeq = closeMaxSeq;
		}
		@Override
		public String toString() {
			return "HeartConfig [seq=" + seq + ", closeMaxSeq=" + closeMaxSeq + "]";
		}
	}

	public interface IdleConnectionKey {
		/**
		 * 最后异常读消息时间
		 */
		public static final String LastReadTimeMills="LastReadTimeMills";
		/**
		 * 心跳配置
		 */
		public static final String HeartConfig="HeartConfig";
	}
	
	/**
	 * 主动心跳请求发送
	 */
	@Override
	public long getWriterIdleTimeMills() {
		return HeartIntervalMills;
	}
	
	/**
	 * (保证5-10秒内有消息来回)
	 * 没有任何请求或者3次心跳请求没有回复则关闭连接(2次会有临界点,如果回复比较快)
	 */
	@Override
	public long getReaderIdleTimeMills() {
		return HeartIntervalMills;
	}
	
	/**
	 * 3次心跳时间没有操作则关闭连接
	 */
	@Override
	public long getAllIdleTimeMills() {
		return getWriterIdleTimeMills()*4;
	}

	@Override
	public final void event_AllIdleTimeOut(JConnection connection) {
		if(!isHeartEnable())
		{
			return ;
		}
		//如果心跳超时则关闭连接
		_log.warn("读写超时,关闭链路:"+connection);
		connection.close();
	}

	@Override
	public final void event_ReadIdleTimeOut(JConnection connection) {
		if(!isHeartEnable())
		{
			return ;
		}
		connection.getAttribute(IdleConnectionKey.HeartConfig);
		HeartConfig hc=null;
		if(connection.hasAttribute(IdleConnectionKey.HeartConfig))
		{
			hc=(HeartConfig) connection.getAttribute(IdleConnectionKey.HeartConfig);
		}else
		{
			hc=new HeartConfig();
			connection.setAttribute(IdleConnectionKey.HeartConfig,hc);
		}
		if(hc.getSeq()>=hc.getCloseMaxSeq())
		{
			_log.warn("读超时,hc:"+hc+",关闭链路:"+connection+",LastReadTimeMills="+connection.getAttribute(IdleConnectionKey.LastReadTimeMills));
			connection.close();
			return ;
		}
		sendHeartReq(connection);
		hc.setSeq(hc.getSeq()+1);
		_log.trace("读超时,hc:"+hc+",发送心跳请求:"+connection);
	}
	
	@Override
	public final void event_WriteIdleTimeOut(JConnection connection) {
		if(!isHeartEnable())
		{
			return ;
		}
		_log.trace("写超时,发送心跳请求:"+connection);
		sendHeartReq(connection);
	}
	
	@Override
	public final void messageArrived(JConnection conn,T msg) {
		try {
			conn.setAttribute(IdleConnectionKey.LastReadTimeMills,System.currentTimeMillis());
			resetHeartSeq(conn);
		}catch (Exception e) {
			_log.error(e.getMessage(),e);
		}
		if(isHeartReq(msg))
		{
			sendHeartRsp(conn);
			return;
		}
		doMessageArrived(conn, msg);
	}
	
	/**
	 * 重置心跳发送序号
	 * @param conn
	 */
	protected final void resetHeartSeq(JConnection conn)
	{
		if(conn.hasAttribute(IdleConnectionKey.HeartConfig))
		{//重置心跳序号
			HeartConfig hc=(HeartConfig) conn.getAttribute(IdleConnectionKey.HeartConfig);
			hc.setSeq(0);
		}
	}

	/**
	 * 发送心跳请求
	 */
	protected abstract void sendHeartReq(JConnection connection);

	/**
	 * 发送心跳回复
	 */
	protected abstract void sendHeartRsp(JConnection connection);
	
	/**
	 * 是否是心跳请求
	 * @param msg
	 * @return
	 */
	protected abstract boolean isHeartReq(T msg);

	/**
	 * 处理收到的消息
	 * @param conn
	 * @param msg
	 */
	protected abstract void doMessageArrived(JConnection conn,T msg);
}
