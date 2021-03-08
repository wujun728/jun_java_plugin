package cn.com.bluemoon.threads;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 拒绝策略应该考虑到业务场景，返回响应的提示或者友好的跳转
 * 如下示例为简单实例，在实际应用中应该根据业务场景进行调整
 * @author Wujun
 * @date 2019年5月24日 上午11:54:09
 *
 */
public class UserRejectHandler implements RejectedExecutionHandler {

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		 System.out.println("task rejected. " + executor.toString());
	}

}
