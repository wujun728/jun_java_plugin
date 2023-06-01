package com.reger.tcc.rollback;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.MethodCallback;

import com.reger.tcc.annotation.RollBack;
import com.reger.tcc.core.TccTransaction;

public class RollBackBeanPostProcessor implements BeanPostProcessor {

	private static final Logger log = LoggerFactory.getLogger(RollBackBeanPostProcessor.class);

	public static final String BEAN_NAME = "reger-tcc-Roll-Back-Bean-Post-Processor";

	private static final Map<String, List<TccRollBack>> tccRollBackMap = new ConcurrentHashMap<String, List<TccRollBack>>();
	private static ParameterNameDiscoverer parameterNameDiscoverer;
	
	@Autowired
	public void setParameterNameDiscoverer(ParameterNameDiscoverer parameterNameDiscoverer) {
		RollBackBeanPostProcessor.parameterNameDiscoverer = parameterNameDiscoverer;
	}
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		this.traverseCancel(bean, beanName);
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	private void traverseCancel(final Object bean, final String beanName) {
		final Class<? extends Object> clazz = bean.getClass();
		ReflectionUtils.doWithMethods(clazz, new MethodCallback() {
			@Override
			public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
				RollBackBeanPostProcessor.this.initCancel(bean, beanName, method);
			}
		});
	}

	private synchronized void initCancel(Object bean, String beanName, Method method) {
		RollBack rollBack = AnnotationUtils.findAnnotation(method, RollBack.class);
		if (rollBack == null) {
			return;
		}
		String cancelName = rollBack.value();
		List<TccRollBack> tccRollBacks;
		if (tccRollBackMap.containsKey(cancelName)) {
			tccRollBacks = tccRollBackMap.get(cancelName);
			log.info("事务回滚方法{}定义了多次，本次在 beanName {}", cancelName, beanName);
		} else {
			tccRollBacks = new ArrayList<TccRollBack>();
		}
		method.setAccessible(false);
		tccRollBacks.add(new TccRollBack(bean, beanName, method, rollBack));
		log.debug("事务回滚方法{}定义在类{}中定义", rollBack.value(), beanName);
		tccRollBackMap.put(cancelName, tccRollBacks);
	}

	public static final <T> boolean rollBack(final String rollBackName, final Map<String, Object> args) {
		if (!tccRollBackMap.containsKey(rollBackName)) {
			log.info("没有找到这个回滚方法的定义 ,rollBack={}", rollBackName);
			return false;
		}
		final List<TccRollBack> tccRollBacks = tccRollBackMap.get(rollBackName);
		return rollBack(tccRollBacks, args);

	}
	
	private static final String[] getMethodParameterNames(Method method) {
		return parameterNameDiscoverer.getParameterNames(method);
	}
	private static Object[] getArgs(Method method,Map<String, Object> args) {
		String[] pms = getMethodParameterNames(method);
		Object[] objectArgs=new Object[pms.length];
		Class<?>[] pts = method.getParameterTypes();
		for (int index = 0; index < pms.length; index++) {
			String pName = pms[index];
			Class<?> type = pts[index];
			Object arg = args.get(pName);
			if(arg!=null&&!type.isInstance(arg) ){
				log.warn("{}.{}参数 {}类型{}和入参{}不兼容，无法输入参数调用，参数被丢弃" ,method.getDeclaringClass(),method.getName(),type ,pName,arg);
				arg=null;
			}
			objectArgs[index]=arg;
		}
		return objectArgs;
	}
	
	private static final boolean rollBack(List<TccRollBack> tccRollBacks, final Map<String, Object> args) {
		boolean SUCCEED = true;
		for (int index = 0; index < tccRollBacks.size(); index++) {
			final TccRollBack rollBack = tccRollBacks.get(index);
			TransactionOperations operations = rollBack.getTransactionTemplate();
			boolean succeed = operations.execute(new TransactionCallback<Boolean>() {
				@Override
				public Boolean doInTransaction(TransactionStatus status) {
					if (!invoke(rollBack, getArgs(rollBack.getMethod(), args))) {
						log.warn("tcc回滚时局部事务异常,局部事务被回滚 ,cancel={}", rollBack.getRollBack());
						status.setRollbackOnly();
						return false;
					}
					return true;
				}
			});
			SUCCEED = SUCCEED && succeed;
		}
		return SUCCEED;
	}

	private static final boolean invoke(TccRollBack rollBack, Object... args) {
		Object bean = rollBack.getBean();
		Method method = rollBack.getMethod();
		try {
			method.invoke(bean, args);
			return true;
		} catch (IllegalArgumentException e) {
			log.error("回滚方法参数参数输入错误{}", rollBack, e);
		} catch (IllegalAccessException e) {
			log.error("回滚方法调用失败{}", rollBack, e);
		} catch (InvocationTargetException e) {
			log.error("回滚方法调用失败{}", rollBack, e);
		} catch (Exception e) {
			log.error("回滚异常{}", rollBack, e);
		}
		return false;
	}

	public static void rollBack(TccTransaction transaction) {
		String rollBackName = transaction.getRollBackName();
		rollBack(rollBackName, transaction.getExpand());
		transaction.rollBack("");
	}
}
