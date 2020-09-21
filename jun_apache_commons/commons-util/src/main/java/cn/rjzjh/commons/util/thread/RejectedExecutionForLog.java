package cn.rjzjh.commons.util.thread;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.rjzjh.commons.util.apiext.ReflectAsset;

/****
 * 当线程被拒绝时采取的策略，如果线程实现cn.rjzjh.commons.util.thread.ICancelHandle接口则调用
 * 
 * @author Administrator
 * 
 */
public class RejectedExecutionForLog implements RejectedExecutionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		logger.error("线程池拒绝，" + "TaskCount:" + executor.getTaskCount()
				+ " ActiveCount:" + executor.getActiveCount()
				+ " CorePoolSize:" + executor.getCorePoolSize());

		if (!executor.isShutdown()) {
			if (ReflectAsset.isInterface(r.getClass(),
					"cn.rjzjh.commons.util.thread.ICancelHandle")) {
				ICancelHandle cancelDo = (ICancelHandle) r;
				cancelDo.doCancle();
			}
			// executor.submit(r);
		}

		// BeanUtils.getProperty(aa, "callable");
		/*
		 * 替换失败 aa.cancel(true);
		 * 
		 * NullBusiImpl retImpl = new NullBusiImpl(
		 * "Exception_greaterMaxThread", "超过了最大纯程"); BaseExe tttt = new
		 * BaseExe(retImpl,null,null); tttt.isLoop=false;
		 * 
		 * r =new FutureTask<CusDynaBean>(tttt);
		 */

		/*
		 * try { Field fieldX = aa.getClass().getDeclaredField("sync");
		 * fieldX.setAccessible(true); Object x = (Object) fieldX.get(aa);
		 * 
		 * Field fieldy = x.getClass().getDeclaredField("callable");
		 * fieldy.setAccessible(true); // BaseExe y = (BaseExe)fieldy.get(x);
		 * NullBusiImpl retImpl = new NullBusiImpl(
		 * "Exception_greaterMaxThread", "超过了最大纯程"); fieldy.set(x, retImpl);
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 */

		// TODO 打印日志、发邮件、设计好返回值等动作。尽量不做下面动作，否则会导致主线程长时间等待
		/*
		 * if (!executor.isShutdown()) { r.run(); }
		 */
	}

}
