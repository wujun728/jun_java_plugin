package com.reger.tcc.core;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.reger.tcc.annotation.Commit;
import com.reger.tcc.annotation.RollBack;

public class TransactionTemplateUtils {

	private static final Logger log = LoggerFactory.getLogger(TransactionTemplateUtils.class);

	private static final Map<String, PlatformTransactionManager> platformTransactionManagers = new ConcurrentHashMap<String, PlatformTransactionManager>();

	private static final Map<String, TransactionOperations> transactionTemplates = new ConcurrentHashMap<String, TransactionOperations>();

	private static String defaultTransactionManagerName;

	public static void setDefaultTransactionManagerName(String defaultTransactionManagerName) {
		TransactionTemplateUtils.defaultTransactionManagerName = defaultTransactionManagerName;
	}

	public static final Map<String, PlatformTransactionManager> getPlatformtransactionmanagers() {
		return platformTransactionManagers;
	}

	private synchronized static final String getTransactionTemplateCacheKey(String transactionManagerName,
			int isolationLevel, boolean readOnly, int timeout) {
		return String.format("transactionManagerName={},isolationLevel={},readOnly={},timeout={}",
				transactionManagerName, isolationLevel, readOnly, timeout);
	}

	public synchronized static final TransactionOperations getTransactionTemplate(String transactionManagerName,
			Isolation isolation, boolean readOnly, int timeout) {
		if (StringUtils.isEmpty(transactionManagerName)) {
			transactionManagerName = TransactionTemplateUtils.defaultTransactionManagerName;
		}
		String cacheKey = getTransactionTemplateCacheKey(transactionManagerName, isolation.value(), readOnly, timeout);
		if (transactionTemplates.containsKey(cacheKey)) {
			return transactionTemplates.get(cacheKey);
		}
		Assert.isTrue(platformTransactionManagers.containsKey(transactionManagerName),
				"事务" + transactionManagerName + "管理器不存在");
		log.debug("创建事务模板 transactionManagerName={}", transactionManagerName);
		PlatformTransactionManager transactionManager = platformTransactionManagers.get(transactionManagerName);
		TccTransactionTemplate transactionTemplate = new TccTransactionTemplate(transactionManager);
		transactionTemplate.setIsolationLevel(isolation.value());
		transactionTemplate.setReadOnly(readOnly);
		transactionTemplate.setTimeout(timeout);
		transactionTemplates.put(cacheKey, transactionTemplate);
		return transactionTemplate;
	}

	public static final TransactionOperations getTransactionTemplate(Commit confirm) {
		String transactionManagerName = confirm.transactionManager();
		Isolation isolation = confirm.isolation();
		boolean readOnly = confirm.readOnly();
		int timeout = confirm.timeout();
		return getTransactionTemplate(transactionManagerName, isolation, readOnly, timeout);
	}

	public static final TransactionOperations getTransactionTemplate(RollBack rollBack) {
		String transactionManagerName = rollBack.transactionManager();
		Isolation isolation = rollBack.isolation();
		boolean readOnly = rollBack.readOnly();
		int timeout = rollBack.timeout();
		return getTransactionTemplate(transactionManagerName, isolation, readOnly, timeout);
	}

	public static final void initTransactionManager(ConfigurableListableBeanFactory beanFactory) {
		Map<String, PlatformTransactionManager> map = beanFactory.getBeansOfType(PlatformTransactionManager.class);
		TransactionTemplateUtils.getPlatformtransactionmanagers().putAll(map);
		Iterator<Entry<String, PlatformTransactionManager>> it = map.entrySet().iterator();
		String defaultTransactionManagerName = null;
		PlatformTransactionManager defaultTransactionManager = getDefaultTransactionManager(beanFactory);
		while (it.hasNext()) {
			Map.Entry<String, PlatformTransactionManager> entry = (Map.Entry<String, PlatformTransactionManager>) it
					.next();
			if (defaultTransactionManagerName == null || defaultTransactionManager == entry.getValue()) {
				defaultTransactionManagerName = entry.getKey();
			}
		}
		TransactionTemplateUtils.setDefaultTransactionManagerName(defaultTransactionManagerName);
	}

	private static PlatformTransactionManager getDefaultTransactionManager(
			ConfigurableListableBeanFactory beanFactory) {
		try {
			return beanFactory.getBean(PlatformTransactionManager.class);
		} catch (Exception e) {
			return null;
		}
	}
}