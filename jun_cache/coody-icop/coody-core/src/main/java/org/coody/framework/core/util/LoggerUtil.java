package org.coody.framework.core.util;

import java.lang.reflect.Method;

import org.coody.framework.core.annotation.LogHead;
import org.coody.framework.core.constant.LoggerConstant;
import org.coody.framework.core.container.ThreadContainer;

public class LoggerUtil {

	
	public static String getClassLog(Class<?> clazz) {
		LogHead handle = clazz.getAnnotation(LogHead.class);
		if (handle == null) {
			return clazz.getSimpleName();
		}
		return handle.value();
	}
	public static String getMethodLog(Method method) {
		LogHead handle = method.getAnnotation(LogHead.class);
		if (handle == null) {
			return null;
		}
		return handle.value();
	}
	public static void writeLog(String module) {
		ThreadContainer.set(LoggerConstant.LOGGER_WRAPPER, module);
	}
	
	public static String minusLog() {
		String logHead = ThreadContainer.get(LoggerConstant.LOGGER_WRAPPER);
		if (logHead == null) {
			return "";
		}
		String tabs[] = logHead.split("_");
		if (tabs.length == 1) {
			ThreadContainer.set(LoggerConstant.LOGGER_WRAPPER, "");
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tabs.length - 1; i++) {
			if (!StringUtil.isNullOrEmpty(sb)) {
				sb.append("_");
			}
			sb.append(tabs[i]);
		}
		ThreadContainer.set(LoggerConstant.LOGGER_WRAPPER, sb.toString());
		return sb.toString();
	}
	

	public static String getCurrLog() {
		return ThreadContainer.get(LoggerConstant.LOGGER_WRAPPER);
	}
}
