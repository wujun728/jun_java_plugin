package net.jueb.util4j.test;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理所有类类型
 * @author Administrator
 */
public class HotSwapMannager {

	ConcurrentHashMap<String,HotSwap> objs=new ConcurrentHashMap<String, HotSwap>();
	
	/**
	 * HotSwap实例包装类
	 * @author Administrator
	 */
	private final class HotSwapLite extends HotSwap
	{
		private HotSwap hs=null; 
		
		public HotSwapLite(HotSwap hs) {
			this.hs=hs;
		}
		
		@Override
		public boolean isUsing() {
			return hs.isUsing();
		}
		@Override
		public long getVersion() {
			return hs.getVersion();
		}
		@Override
		public void show() {
			hs.show();
		}
		@Override
		public long getUniqueId() {
			return hs.getUniqueId();
		}
	}
	
	/**
	 * 返回限定类型
	 * @param uniqueId
	 * @return
	 */
	public HotSwapLite getHotSwap(int uniqueId)
	{
		return new HotSwapLite(new H1());
	}
	
	public HotSwap update()
	{
		return null;
	}
	
	
}
