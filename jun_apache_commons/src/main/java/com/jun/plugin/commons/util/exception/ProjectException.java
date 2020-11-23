package com.jun.plugin.commons.util.exception;

import java.util.Locale;

import org.mvel2.templates.TemplateRuntime;

import com.jun.plugin.commons.util.MessageUtils;
import com.jun.plugin.commons.util.callback.IConvertValue;

/**
 * @ClassName: ProjectException
 * @Description: 整个项目的异常基类，errorCode不允许修改且必需是ExceptAll所枚举的异常编码。<br>
 *               errorMessage可以修改，如果不修改且是ExceptAll枚举的desc字段。
 * @author Wujun
 * @date 2010-10-29 下午03:45:52
 */

@SuppressWarnings("rawtypes")
public class ProjectException extends Exception {
	private static final long serialVersionUID = 6143648298220882870L;
	protected String errorMessage;
	protected Throwable cause;

	public ProjectException(Enum exceptionAll) {
		super(exceptionAll.name());
		this.errorMessage = exceptionAll.name();
	}

	/**
	 * @param ExceptAll
	 *            exceptAll
	 * @param errBean
	 *            错误的参数bean
	 * */
	public ProjectException(Enum exceptionAll, ParamInfoBean errBean,
			String lang) {
		super(exceptionAll.name());
		String temp = MessageUtils.get(lang, "exception.param.default");
		this.errorMessage = (String) TemplateRuntime.eval(temp, errBean);
	}

	public ProjectException(Enum exceptionAll, Throwable cause) {
		super(exceptionAll.name(), cause);
		this.errorMessage = exceptionAll.name();
		this.cause = cause;
	}

	/**
	 * 当有自定义的错误原因时可用此构造函数
	 * */
	public ProjectException(Enum exceptionAll, String errMsg) {
		super(exceptionAll.name());
		this.errorMessage = errMsg;
	}
	
	public String getMessage(IConvertValue convertValue){
		return convertValue.getStr(super.getMessage());
	}

	//@Override
	public String getShowMessage() {
		String lang = Locale.getDefault().getLanguage();
		return getShowMessage(lang);
	}

	public String getShowMessage(String lang) {
		String restr = MessageUtils.get(lang, "exception.hint.reason");
		String errstr = MessageUtils.get(lang, "exception.hint.errorcodemsg");

		StringBuffer sb = new StringBuffer();
		if (this.cause != null && !(this.cause instanceof ProjectException)) {
			sb.append(restr + "\n");
			StackTraceElement[] es = this.cause.getStackTrace();
			for (int i = 0; i < es.length; i++) {
				sb.append(es[i] + "\n");
			}
		}
		sb.append(errstr + "[code=" + super.getMessage() + "],[message="
				+ getErrorMessage() + "]");
		return sb.toString();
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}
}
