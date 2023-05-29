package com.jun.plugin.util4j.common.game;

/**
 * 游戏服务
 * @author Administrator
 */
public interface IService {

	public static enum ServiceState{
		
		/**
		 * 停止
		 */
		Stoped(1),
		/**
		 *启动中
		 */
		Starting(2),
		/**
		 * 进行中
		 */
		Active(3),
		/**
		 * 结束中
		 */
		Stoping(4),
		;
		
		private final int value;
		
		private ServiceState(int value) {
			this.value=value;
		}
		
		public int getValue()
		{
			return value;
		}

		public static ServiceState valueOf(int value)
		{
			ServiceState state = Stoped;
			for(ServiceState rs:values())
			{
				if(rs.value==value)
				{
					state = rs;
				}
			}
			return state;
		}
	}
	
	/**
	 *启动服务
	 */
	public void startService();
	
	/**
	 *关闭服务
	 */
	public void closeService();
	
	/**
	 * 获取服务状态
	 * @return
	 */
	public ServiceState getState();
	
	public boolean hasAttribute(String key);

	public void setAttribute(String key, Object value);

	public Object getAttribute(String key);

	public Object removeAttribute(String key);

	public void clearAttributes();
}
