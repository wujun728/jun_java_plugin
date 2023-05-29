package com.jun.plugin.util4j.common.game.uuid;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 服务器唯一ID生成器
 * @author Administrator
 */
public class UidFactory {
	
	private static final AtomicInteger seq=new AtomicInteger();
	
	/**
	 * serverId(16位)-sec(32位)-seq(16位)
	 * @param serverId 支持65535个服务器
	 * @return
	 */
	public static long getId(int serverId)
	{
		synchronized (seq) {
			long second=System.currentTimeMillis()/1000;
			System.out.println("second:"+second);
			int inc=seq.incrementAndGet();
			System.out.println("inc:"+inc);
			return ((long)(serverId & 0xFFFF))<< 48 | 
					((second & 0x00000000FFFFFFFF) <<16) | (inc & 0x0000FFFF);
		}
	}
	
	public static int getServerIdById(long id)
	{
		return (int) (id >>48 & 0xFFFF);
	}
	
	public static int getTimeById(long id)
	{
		return (int) (id >> 16 & 0xFFFFFFFF);
	}
	
	public static int getSeqById(long id)
	{
		return (int) (id & 0xFFFF);
	}
}
