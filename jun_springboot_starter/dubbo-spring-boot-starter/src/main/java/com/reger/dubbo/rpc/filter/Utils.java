package com.reger.dubbo.rpc.filter;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.common.utils.ConcurrentHashSet;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcResult;

public class Utils {

	private static final Logger log = LoggerFactory.getLogger(Utils.class);

	private static final String TRANSFORM_EXCEPTION_MESSAGE = "REGER.DUBBO.RPC.TRANSFORM_EXCEPTION_MESSAGE";

	private static final Set<Class<? extends Throwable>> exceptions = new ConcurrentHashSet<Class<? extends Throwable>>();

	/**
	 * 注册dubbo可以进行传递的异常
	 * @param claz  claz
	 */
	public final static void register(Class<? extends Throwable> claz) {
		exceptions.add(claz);
	}

	protected final static Result decodeException(Result relust) {
		if (relust != null && relust.hasException()) {
			Throwable throwable = relust.getException();
			if (throwable.getClass().equals(Exception.class) && TRANSFORM_EXCEPTION_MESSAGE.equals(throwable.getMessage())) {
				throwable = throwable.getCause();
				if (throwable != null) {
					log.debug("被包装的异常{}，解开包装...", throwable.getClass());
					return new RpcResult(throwable);
				}
			}
		}
		return relust;
	}

	/**
	 * 对服务端的异常进行编码
	 * 
	 * @param relust relust
	 * @return relust
	 */
	protected final static Result encoderException(Result relust) {
		if (relust != null && relust.hasException()) {
			Throwable throwable = relust.getException();
			if (exceptions.contains(throwable.getClass())) {
				log.debug("{}异常被转化为Exception输出，用来通用异常处理....",throwable.getClass());
				return new RpcResult(new Exception(TRANSFORM_EXCEPTION_MESSAGE, throwable));
			}
		}
		return relust;
	}
}
