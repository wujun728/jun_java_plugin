package org.springrain.cms.util;

import freemarker.template.TemplateModelException;

/**
 * 非数字参数异常
 */
@SuppressWarnings("serial")
public class MustSplitNumberException extends TemplateModelException {
	public MustSplitNumberException(String paramName) {
		super("The \"" + paramName
				+ "\" parameter must be a number split by ','");
	}

	public MustSplitNumberException(String paramName, Exception cause) {
		super("The \"" + paramName
				+ "\" parameter must be a number split by ','", cause);
	}
}
