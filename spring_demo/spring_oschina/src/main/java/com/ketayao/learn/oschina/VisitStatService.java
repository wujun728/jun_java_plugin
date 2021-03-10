package com.ketayao.learn.oschina;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 访问统计服务
 */
public class VisitStatService extends TimerTask {

	private final static Logger log = LoggerFactory.getLogger(VisitStatService.class);
	private static boolean start = false;
	private static VisitStatService daemon;
	private static Timer click_timer;
	private final static long INTERVAL = 60 * 1000;
	
	/**
	 * 支持统计的对象类型
	 */
	private final static byte[] TYPES = new byte[]{
		0x01,0x02,0x03,0x04,0x05
	};
	
	//内存队列
	private final static ConcurrentHashMap<Byte, ConcurrentHashMap<Long, Integer>> queues = 
		new ConcurrentHashMap<Byte, ConcurrentHashMap<Long, Integer>>(){{
			for(byte type : TYPES)
				put(type, new ConcurrentHashMap<Long, Integer>());
		}};

	/**
	 * 记录访问统计
	 * @param type
	 * @param obj_id
	 */
	public static void record(byte type, long obj_id) {
		ConcurrentHashMap<Long, Integer> queue = queues.get(type);
		if(queue != null){
			Integer nCount = queue.get(obj_id);
			nCount = (nCount==null)?1:nCount+1;
			queue.put(obj_id, nCount.intValue());
			System.out.printf("record (type=%d,id=%d,count=%d)\n",type,obj_id,nCount);
		}
	}

	/**
	 * 启动统计数据写入定时器
	 * @param ctx
	 */
	public static void start() {
		if(!start){
			daemon = new VisitStatService();
			click_timer = new Timer("VisitStatService", true);
			click_timer.schedule(daemon, INTERVAL, INTERVAL);//运行间隔1分钟
			start = true;
		}
		log.info("VisitStatService started.");
	}

	/**
	 * 释放Service
	 */
	public static void destroy(){
		if(start){
			click_timer.cancel();
			start = false;
		}
		log.info("VisitStatService stopped.");
	}
	
	@Override
	public void run() {
		for(byte type : TYPES){
			ConcurrentHashMap<Long, Integer> queue = queues.remove(type);
			queues.put(type, new ConcurrentHashMap<Long, Integer>());
			try{
				_flush(type, queue);
			}catch(Throwable t){
				log.error("Failed to flush click stat data.", t);
				//此处发送异常报告
			}finally{
				//此处关闭数据库连接
			}
		}
	}
	
	@Override
	public boolean cancel() {
		boolean b = super.cancel();
		//写回剩余数据，Tomcat停止时不会丢失数据
		this.run();
		return b;
	}
	
	/**
	 * 写访问统计数据到数据库
	 * @param type
	 * @param queue
	 */
	private void _flush(byte type, ConcurrentHashMap<Long, Integer> queue){
		if(queue.size()==0)
			return ;
		switch(type){
			//数据写入数据库....
		}
		System.out.printf("Flush to database: type=%d\n", type);
	}

	/**
	 * 测试
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		start();
		for(int i=0;i<10;i++)
			new Timer("OfferTask_"+(i+1), false).schedule(
					new TimerTask(){
						private Random rnd = new Random(System.currentTimeMillis());			
						@Override
						public void run() {
							record(TYPES[rnd.nextInt(TYPES.length)],rnd.nextInt(10));
						}		
					}, 
				0, 1000
			);
	}
	
}