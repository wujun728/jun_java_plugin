package com.springboot.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@Configuration
public class TxAnoConfig {
	/**
	 * 事务拦截类型
	 * 
	 * @return
	 */
	@Bean("txSource")
	public TransactionAttributeSource transactionAttributeSource() {
		NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
		/* 只读事务，不做更新操作 */
		RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
		readOnlyTx.setReadOnly(true);
		readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
		RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute(
				TransactionDefinition.PROPAGATION_REQUIRED,
				Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
		requiredTx.setTimeout(5);
		Map<String, TransactionAttribute> txMap = new HashMap<>();
		txMap.put("add*", requiredTx);
		txMap.put("save*", requiredTx);
		txMap.put("insert*", requiredTx);
		txMap.put("update*", requiredTx);
		txMap.put("delete*", requiredTx);
		txMap.put("get*", readOnlyTx);
		txMap.put("query*", readOnlyTx);
		txMap.put("*", readOnlyTx);
		source.setNameMap(txMap);

		return source;
	}

	/**
	 * 切面拦截规则 参数会自动从容器中注入
	 * 
	 * @param txInterceptor
	 * @return
	 */
	@Bean
	public AspectJExpressionPointcutAdvisor pointcutAdvisor(TransactionInterceptor txInterceptor) {
		AspectJExpressionPointcutAdvisor pointcutAdvisor = new AspectJExpressionPointcutAdvisor();
		pointcutAdvisor.setAdvice(txInterceptor);
		pointcutAdvisor.setExpression("execution (* com.springboot.service.*.*(..))");
		return pointcutAdvisor;
	}

	/**
	 * 事务拦截器
	 * 
	 * @param tx
	 * @return
	 */
	@Bean("txInterceptor")
	TransactionInterceptor getTransactionInterceptor(PlatformTransactionManager tx) {
		return new TransactionInterceptor(tx, transactionAttributeSource());
	}
}
