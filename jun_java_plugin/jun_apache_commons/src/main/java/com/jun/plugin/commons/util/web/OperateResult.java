package com.jun.plugin.commons.util.web;

import org.json.JSONObject;

import com.jun.plugin.commons.util.apiext.StringUtil;
import com.jun.plugin.commons.util.callback.IConvertValue;
import com.jun.plugin.commons.util.exception.ProjectException;

/**
 * 操作返回的对象
 * */
public class OperateResult {
	private int result;// 1:正确,0:出错
	private String message = "";
	private String jsonMsg;
	private ProjectException opeExcept;
	private Object[] retObjs;// 操作成功后，如果想带一些返回值在此设置

	/**
	 * 由异常来构建返回结果
	 * */
	public OperateResult(ProjectException opeExcept) {
		this.result = 0;// 发生异常返回结果肯定为０
		this.opeExcept = opeExcept;
		if (opeExcept != null) {
			this.message = opeExcept.getErrorMessage();
		}
	}

	public boolean isSuc() {
		return result == 1;
	}

	public OperateResult(int result, ProjectException opeExcept) {
		this.result = result;
		this.opeExcept = opeExcept;
		if (opeExcept != null) {
			this.message = opeExcept.getErrorMessage();
		}
	}

	public OperateResult(int result, String message) {
		this.result = result;
		this.message = message;
	}

	public OperateResult(int result) {
		this.result = result;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	/****
	 * 国际化
	 * @param locale
	 * @return
	 */
	public String getMessage(IConvertValue convertValue) {
		if(convertValue==null){
			return message;
		}
		return convertValue.getStr(message);
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ProjectException getOpeExcept() {
		return opeExcept;
	}

	public void setOpeExcept(ProjectException opeExcept) {
		this.opeExcept = opeExcept;
	}

	public String getJsonMsg(IConvertValue convertValue) {
		if (StringUtil.isNull(jsonMsg)) {
			// 有些场景返回的是JSON，当中有双引号,需要转义
			String temp = getMessage(convertValue);
			if(StringUtil.isNotNull(temp)){
				temp = temp.replace("\"", "\\\"");
			}
			jsonMsg = "{\"result\":" + result + ",\"msg\":\"" + temp + "\"}";
		}
		return jsonMsg;
	}
	
	public JSONObject getJsonObj(){
//		return new JSONObject("result",result,"msg",message);
		return new JSONObject("result");
	}

	public void setJsonMsg(String jsonMsg) {
		this.jsonMsg = jsonMsg;
	}

	public Object[] getRetObjs() {
		return retObjs;
	}

	public Object getRetObj(int index) {
		if (retObjs == null || retObjs.length <= index) {
			return null;
		}
		return retObjs[index];
	}

	public void setRetObjs(Object[] retObjs) {
		this.retObjs = retObjs;
	}
}
