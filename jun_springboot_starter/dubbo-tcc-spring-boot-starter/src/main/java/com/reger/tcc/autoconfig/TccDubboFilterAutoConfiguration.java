package com.reger.tcc.autoconfig;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

import com.alibaba.dubbo.rpc.Result;
import com.reger.dubbo.rpc.filter.ConsumerFilter;
import com.reger.dubbo.rpc.filter.JoinPoint;
import com.reger.dubbo.rpc.filter.ProviderFilter;
import com.reger.tcc.core.TccConst;

public class TccDubboFilterAutoConfiguration {
	
	private static final Logger log = LoggerFactory.getLogger(TccDubboFilterAutoConfiguration.class);

	@Bean("z.tcc.ConsumerFilter")
	public ConsumerFilter consumerFilter() {
		log.debug("tcc事务ConsumerFilter开始初始化...");
		return new ConsumerFilter() {
			@Override
			public Result invoke(JoinPoint<?> point) {
				String tccTransactionId = TccConst.TCC_THREADLOCAL.get();
				Integer depth = null;
				if (StringUtils.isEmpty(tccTransactionId)) {
					Map<String, String> attachments = point.getAttachments();
					depth = TccConst.TCC_DEPTH_THREADLOCAL.get();
					attachments.put(TccConst.TCC_TRANSACTION_ID, tccTransactionId);
					attachments.put(TccConst.TCC_TRANSACTION_DEPTH, String.valueOf(depth));
					log.debug("远程调用前发送tcc事务  transactionId ={} ,depth={}", tccTransactionId, depth);
				}
				try {
					return point.proceed();
				} finally {
					if (depth != null && depth == 0) {
						TccConst.TCC_DEPTH_THREADLOCAL.remove();
					}

				}
			}
		};
	}

	@Bean("0.tcc.ProviderFilter")
	public ProviderFilter providerFilter() {
		log.debug("tcc事务ProviderFilter开始初始化...");
		return new ProviderFilter() {
			@Override
			public Result invoke(JoinPoint<?> point) {
				String tccId = point.getAttachment(TccConst.TCC_TRANSACTION_ID);
				String depth = point.getAttachment(TccConst.TCC_TRANSACTION_DEPTH);
				if (!StringUtils.isEmpty(tccId)) {
					TccConst.TCC_THREADLOCAL.set(tccId);
					TccConst.TCC_DEPTH_THREADLOCAL.set(toInteger(depth)+1);
					log.debug("收到远程调用的tcc事务  transactionId ={} , transactionDepth={}", tccId,depth);
				}
				try {
					return point.proceed();
				} finally {
					if (!StringUtils.isEmpty(tccId)) {
						TccConst.TCC_THREADLOCAL.remove();
						TccConst.TCC_DEPTH_THREADLOCAL.remove();
					}
				}
			}

			private final int toInteger(String depth) {
				try {
					return Integer.parseInt(depth);
				} catch (NumberFormatException e) {
					return 0;
				}
			}
		};
	}

}
