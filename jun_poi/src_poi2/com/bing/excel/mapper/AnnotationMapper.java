package com.bing.excel.mapper;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


import com.bing.excel.annotation.BingConvertor;
import com.bing.excel.annotation.CellConfig;
import com.bing.excel.converter.FieldValueConverter;
import com.bing.excel.converter.ConverterMatcher;
import com.bing.excel.exception.IllegalCellConfigException;
import com.bing.excel.exception.InitializationException;
import com.bing.excel.exception.MissingCellConfigException;
import com.bing.excel.mapper.ConversionMapper.FieldConverterMapper;
import com.bing.utils.ReflectDependencyFactory;

import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * 创建时间：2015-12-11下午8:33:01 项目名称：excel
 * 
 * @author shizhongtao
 * @version 1.0
 * @since JDK 1.7 文件名称：AnnotationMapper.java 类说明：
 */
public class AnnotationMapper implements ExcelConverterMapperHandler {

	// 属性转换器的缓存
	private Cache<Class<?>, Map<List<Object>, FieldValueConverter>> converterCache = null;
	private  ConversionMapper objConversionMapper = new ConversionMapper();
	private final Set<Class<?>> annotatedTypes = Collections
			.synchronizedSet(new HashSet<Class<?>>());

	// private transient Object[] arguments;

	public AnnotationMapper() {
		converterCache = CacheBuilder.newBuilder().maximumSize(500)
				.expireAfterAccess(2, TimeUnit.MINUTES).build();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bing.excel.mapper.OrmMapper#processAnnotations(java.lang.Class[])
	 */
	@Override
	public void processAnnotations(final Class[] initialTypes) {
		if (initialTypes == null || initialTypes.length == 0) {
			return;
		}

		final Set<Class<?>> types = new UnprocessedTypesSet();
		for (final Class initialType : initialTypes) {
			types.add(initialType);
		}
		processTypes(types);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bing.excel.mapper.OrmMapper#processAnnotations(java.lang.Class)
	 */
	@Override
	public void processAnnotations(final Class initialType) {
		if (initialType == null) {
			return;
		}

		final Set<Class<?>> types = new UnprocessedTypesSet();
		types.add(initialType);
		processTypes(types);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bing.excel.mapper.OrmMapper#getLocalConverter(java.lang.Class,
	 * java.lang.String)
	 */

	@Override
	public FieldValueConverter getLocalConverter(Class definedIn,
			String fieldName) {

		return objConversionMapper.getLocalConverter(definedIn, fieldName);
	}

	@Override
	public FieldConverterMapper getLocalFieldConverterMapper(Class definedIn,
			String fieldName) {

		return objConversionMapper.getLocalConverterMapper(definedIn, fieldName);
	}

	

	@Override
	public String getModelName(Class<?> key) {
		return objConversionMapper.getModelName(key);
	}

	private void processTypes(final Set<Class<?>> types) {

		while (!types.isEmpty()) {
			Iterator<Class<?>> iterator = types.iterator();
			final Class<?> type = iterator.next();
			iterator.remove();
			synchronized (type) {
				if (annotatedTypes.contains(type)) {
					continue;
				}
				try {
					// 转换的类型不可能对应的是基本类型
					if (type.isPrimitive()) {
						continue;
					}
					// 目前先不考虑model的接口继承问题 TODO
					if (type.isInterface()
							|| (type.getModifiers() & Modifier.ABSTRACT) > 0) {
						continue;
					}
					final Field[] fields = type.getDeclaredFields();
					for (int i = 0; i < fields.length; i++) {
						final Field field = fields[i];

						if (field.isEnumConstant()
								|| (field.getModifiers() & (Modifier.STATIC | Modifier.TRANSIENT)) > 0) {
							continue;
						}
						// 应该不会出现
						if (field.isSynthetic()) {
							continue;
						}

						addMapper(type,field);
					}

				} finally {
					annotatedTypes.add(type);
				}

			}
		}
	}

	/**
	 * <p>
	 * Title: addConvertor
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param key
	 * @param cls
	 */
	private void addMapper(Class<?>  clazz,Field field) {
		CellConfig cellConfig = field.getAnnotation(CellConfig.class);
		BingConvertor bingConvertor = field.getAnnotation(BingConvertor.class);
		int index;
		String alias;
		boolean readRequired;
		if (cellConfig == null) {
			throw new MissingCellConfigException("["+clazz+"#"+field.getName()+"]Missing CellConfig annotation");
		} else {
			index = cellConfig.index();
			//omitOutput=cellConfig.omitOutput();
			alias=cellConfig.aliasName();
			readRequired=cellConfig.readRequired();
			if(Strings.isNullOrEmpty(alias)){
				alias=field.getName();
			}
			if (index < 0) {
				throw new IllegalCellConfigException("field[" + field.getName()
						+ "] has an error cellConfig,illegal index");
			}
		}
		FieldValueConverter converter = null;
		if (bingConvertor != null) {
			Class<? extends FieldValueConverter> value = bingConvertor.value();
			if (value != null) {
				try {
					converter = cacheConverter(bingConvertor, field.getType());
				} catch (ExecutionException e) {
					throw new InitializationException("No " + value
							+ " available");
				}
			} 
		} 
		objConversionMapper.registerLocalConverter(clazz,
				field.getName(), index,alias, field.getType(),readRequired, converter);
	}

	private FieldValueConverter cacheConverter(final BingConvertor annotation,
			final Class targetType) throws ExecutionException {
		FieldValueConverter result = null;
		final Object[] args;
		final List<Object> parameter = new ArrayList<Object>();

		final List<Object> arrays = new ArrayList<Object>();
		arrays.add(annotation.booleans());
		arrays.add(annotation.bytes());
		arrays.add(annotation.chars());
		arrays.add(annotation.doubles());
		arrays.add(annotation.floats());
		arrays.add(annotation.ints());
		arrays.add(annotation.longs());
		arrays.add(annotation.shorts());
		arrays.add(annotation.strings());
		arrays.add(annotation.types());
		for (Object array : arrays) {
			if (array != null) {
				int length = Array.getLength(array);
				for (int i = 0; i < length; i++) {
					Object object = Array.get(array, i);
					if (!parameter.contains(object)) {
						parameter.add(object);
					}
				}
			}
		}
		final Class<? extends ConverterMatcher> converterType = annotation
				.value();
		Map<List<Object>, FieldValueConverter> converterMapping = converterCache
				.get(converterType,
						new Callable<Map<List<Object>, FieldValueConverter>>() {

							@Override
							public Map<List<Object>, FieldValueConverter> call()
									throws Exception {

								Map<List<Object>, FieldValueConverter> converterMappingTemp = new HashMap<List<Object>, FieldValueConverter>();
								return converterMappingTemp;
							}

						});
		result = converterMapping.get(parameter);
		if (result == null) {
			int size = parameter.size();

			if (size > 0) {
				args = new Object[size];
				System.arraycopy(parameter.toArray(new Object[size]), 0, args,
						0, size);
			} else {
				args = null;
			}
			final FieldValueConverter converter;
			try {

				converter = (FieldValueConverter) ReflectDependencyFactory
						.newInstance(converterType, args);
			} catch (final Exception e) {
				throw new InitializationException(
						"Cannot instantiate converter "
								+ converterType.getName()
								+ (targetType != null ? " for type "
										+ targetType.getName() : ""), e);
			}

			converterMapping.put(parameter, converter);
			result=converter;
		}
		return result;
	}

	

	private final class UnprocessedTypesSet extends LinkedHashSet<Class<?>> {
		@Override
		public boolean add(Class<?> type) {
			if (type == null) {
				return false;
			}
			while (type.isArray()) {
				type = type.getComponentType();
			}
			final String name = type.getName();
			if (name.startsWith("java.") || name.startsWith("javax.")) {
				return false;
			}
			final boolean ret = annotatedTypes.contains(type) ? false : super
					.add(type);
			return ret;
		}
	}
}
