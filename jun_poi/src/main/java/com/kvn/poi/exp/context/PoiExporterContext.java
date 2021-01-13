package com.kvn.poi.exp.context;

import java.util.Map;

import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * 上下文
 * 
 * @author wzy
 * @date 2017年7月11日 下午7:54:10
 */
public class PoiExporterContext {
	private SpelExpressionParser spelExpParser;
	public static final StandardEvaluationContext EVAL_CONTEXT = new StandardEvaluationContext();
	private Map<String, Object> rootObjectMap;

	public PoiExporterContext() {
		super();
	}

	public PoiExporterContext(SpelExpressionParser spelExpressionParser, Map<String, Object> rootObjectMap) {
		this.spelExpParser = spelExpressionParser;
		this.rootObjectMap = rootObjectMap;
	}

	public SpelExpressionParser getSpelExpParser() {
		return spelExpParser;
	}

	public Map<String, Object> getRootObjectMap() {
		return rootObjectMap;
	}
	
}
