package com.reger.tcc.autoconfig;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.alibaba.dubbo.config.spring.util.BeanRegistrar;
import com.reger.tcc.confirm.TccConfirmAspect;
import com.reger.tcc.core.Queue;
import com.reger.tcc.core.Queue.Message;
import com.reger.tcc.core.TccStorage;
import com.reger.tcc.core.TccTransaction;
import com.reger.tcc.core.TransactionTemplateUtils;
import com.reger.tcc.rollback.RollBackBeanPostProcessor;

@Import(TccDubboFilterAutoConfiguration.class)
public class TccAutoConfiguration implements BeanFactoryPostProcessor,CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(TccAutoConfiguration.class);

	@Autowired(required=false)
	private Queue queue;

	@Autowired(required=false)
	private TccStorage tccStorage;
	
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
		BeanRegistrar.registerInfrastructureBean(registry, RollBackBeanPostProcessor.BEAN_NAME,
				RollBackBeanPostProcessor.class);
		TransactionTemplateUtils.initTransactionManager(beanFactory);
	}

	@Bean
	public TccConfirmAspect tccConfirmAspect() {
		log.debug("tcc事务Aspect开始初始化...");
		return new TccConfirmAspect();
	}

	@Override
	public void run(String... args) throws Exception {
		queue.poll(new Message() {
			 @Override
			public void event(String transactionId) {
				 List<TccTransaction> transactions = tccStorage.findByTransactionIdAndCurPlatform(transactionId);
				 for (int index = 0; index < transactions.size(); index++) {
					TccTransaction transaction = transactions.get(index);
					RollBackBeanPostProcessor.rollBack(transaction);
				}
			}
		});
	}
	
	

}
