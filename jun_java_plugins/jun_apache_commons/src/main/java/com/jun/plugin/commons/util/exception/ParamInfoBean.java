package com.jun.plugin.commons.util.exception;

public class ParamInfoBean {
	// public final static String showErrorFormate = MessageUtils.get(lang,
	// "exception.param.default");
	private Integer index;
	private String name;
	private String errStr;
	private String needStr;

	public ParamInfoBean(Integer index, String errStr) {
		super();
		this.index = index;
		this.errStr = errStr;
	}

	public ParamInfoBean(Integer index, String errStr, String needStr) {
		super();
		this.index = index;
		this.errStr = errStr;
		this.needStr = needStr;
	}

	public ParamInfoBean(String errStr) {
		super();
		this.errStr = errStr;
	}

	public ParamInfoBean(String name, String errStr) {
		super();
		this.name = name;
		this.errStr = errStr;
	}

	public ParamInfoBean(String name, String errStr, String needStr) {
		super();
		this.name = name;
		this.errStr = errStr;
		this.needStr = needStr;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getErrStr() {
		return errStr;
	}

	public void setErrStr(String errStr) {
		this.errStr = errStr;
	}

	public String getNeedStr() {
		return needStr;
	}

	public void setNeedStr(String needStr) {
		this.needStr = needStr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
