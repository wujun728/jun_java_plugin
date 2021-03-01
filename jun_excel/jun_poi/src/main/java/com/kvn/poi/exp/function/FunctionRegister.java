package com.kvn.poi.exp.function;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kvn.poi.exp.context.PoiExporterContext;

/**
 * 函数注册器
 * @author wzy
 * @date 2017年7月11日 下午7:57:23
 */
public class FunctionRegister {
	private static final Logger logger = LoggerFactory.getLogger(FunctionRegister.class);
	
	public static void registerInternalFunction(){
		Method[] methods = InternalUtils.class.getDeclaredMethods();
		logger.info("|----------PoiEl注册的内部函数------------|");
		for(Method m : methods){
			String funName = m.getName();
			logger.info("|-----------" + funName + "------------------|");
			PoiExporterContext.EVAL_CONTEXT.registerFunction(funName, m);
		}
		logger.info("|------------------------------------|");
	}
}
