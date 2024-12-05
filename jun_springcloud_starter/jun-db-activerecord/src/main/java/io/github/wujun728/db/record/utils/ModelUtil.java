package io.github.wujun728.db.record.utils;

import cn.hutool.core.bean.BeanUtil;
import io.github.wujun728.db.record.Record;
//import com.jfinal.plugin.activerecord.Model;
//import com.jfinal.plugin.activerecord.Record;

import java.util.HashMap;
import java.util.Map;

/**
 * model 工具
 *
 * 
 */
public class ModelUtil {

	/**
	 * 将 model 转为 bean
	 */
	public static <T> T toBean(Object model, Class<T> valueType) {
		// model 有 get set 方法，可以直接转换
		return BeanUtil.copyProperties(model, valueType);
	}

	/**
	 * 将 record 转为 bean
	 */
	public static <T> T toBean(Record record, Class<T> valueType) {
		// 默认下划线转驼峰
		return toBean(record, FieldStrategy.LOWER_TO_CAMEL, valueType);
	}

	/**
	 * 将 record 转为 bean
	 */
	public static <T> T toBean(Record record, FieldStrategy strategy, Class<T> valueType) {
		Map<String, Object> data = new HashMap<>(16);
		record.getColumns().forEach((key, value) -> data.put(strategy.convert(key), value));
		return BeanUtil.toBean(data, valueType);
	}

}
