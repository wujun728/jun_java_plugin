package org.smartboot.shared.converter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;

/**
 * 通用转换器,要转换的对象间的变量名称要一致
 * 
 * @author Wujun
 * @version CommonConverter.java, v 0.1 2015年11月18日 下午1:56:53 Seer Exp.
 */
public class CommonConverter {
	/** logger日志 */
	private static final Logger LOGGER = LogManager.getLogger(CommonConverter.class);

	/** 通用转换器缓存 */
	private static final Map<String, BeanCopier> beanCopierMap = new HashMap<String, BeanCopier>();

	/** Cglib转换器 */
	private static final Converter converter = new CglibConverter();

	public static <T, E> void convert(T source, E target,Converter converter){
		// 参数校验
		if (source == null || target == null) {
			return;
		}

		String key = source.getClass() + " " + target.getClass();

		BeanCopier beanCopier;
		if (beanCopierMap.get(key) == null) {
			beanCopier = BeanCopier.create(source.getClass(), target.getClass(), true);
			beanCopierMap.put(key, beanCopier);
		} else {
			beanCopier = beanCopierMap.get(key);
		}

		beanCopier.copy(source, target, converter);
	
	}
	/**
	 * 单个对象 source --> target
	 * 
	 * @param <T>
	 * @param <E>
	 * @param source
	 * @param target
	 */
	public static <T, E> void convert(T source, E target) {
		convert(source, target, converter);
	}

	public static <T, E extends Object> void convertList(Class<E> targetClass, Collection<T> sources,
		Collection<E> targets, Converter converter) {


		// 参数校验
		if (targetClass == null || CollectionUtils.isEmpty(sources) || targets == null) {
			return;
		}

		try {
			for (T source : sources) {
				E target = (E) targetClass.newInstance();

				convert(source, target,converter);
				targets.add(target);
			}
		} catch (Exception e) {
			LOGGER.error("转换对象异常", e);
		}
	
	}

	/**
	 * 数组对象转换
	 * 
	 * @param <T>
	 * @param <E>
	 * @param targetClass
	 * @param sources
	 * @param targets
	 */
	public static <T, E extends Object> void convertList(Class<E> targetClass, Collection<T> sources,
		Collection<E> targets) {
		convertList(targetClass, sources, targets, converter);
	}
}
