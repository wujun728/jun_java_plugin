package com.jun.plugin.util4j.cache.callBack.impl;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.jun.plugin.util4j.cache.callBack.CallBack;

/**
 * 解决webapp异步处理
 * @author Administrator
 * @param <C> Callable<C>
 * @param <T> CallBack<T>
 */
public abstract class CallableAdapter<C,T> implements Callable<C>,CallBack<T>{
		private final CountDownLatch latch=new CountDownLatch(1);
		private Optional<T> result;
		private boolean isTimeOut=true;
		
		@Override
		public final void call(boolean timeOut,Optional<T> result) {
			if(timeOut)
			{
				isTimeOut=true;
			}else
			{
				isTimeOut=false;
				this.result=result;
			}
			latch.countDown();//解除阻塞
		}

		@Override
		public final C call() throws Exception {
			if(doAsync())
			{
				latch.await(getTimeOutMills(), TimeUnit.MILLISECONDS);//等待CallBack的回调或者超时解锁
			}
			return doCall(result,isTimeOut);
		}
		
		/**
		 * 阻塞超时时间
		 * @return
		 */
		protected long getTimeOutMills() {
			return DEFAULT_TIMEOUT;
		}
		
		protected abstract C doCall(Optional<T> result,boolean isTimeOut);
		
		/**
		 * 执行异步,返回是否必须执行
		 * @return
		 */
		protected abstract boolean doAsync();
	}