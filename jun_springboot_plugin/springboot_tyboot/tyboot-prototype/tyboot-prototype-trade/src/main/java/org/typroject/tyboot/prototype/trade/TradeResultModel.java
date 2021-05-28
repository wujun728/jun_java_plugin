package org.typroject.tyboot.prototype.trade;

/** 
 * 
 * <pre>
 *  Tyrest
 *  File: TradeResultModel.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  Notes:
 *  $Id: TradeResultModel.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月17日		magintrursh		Initial.
 *
 * </pre>
 */
public class TradeResultModel {
	
	
	private boolean  calledSuccess; //调用状态

	private Object result; //交易对象
	
	private String resultMessage; //结果信息
	
	private String resultCode;


	
	public boolean isCalledSuccess() {
		return calledSuccess;
	}

	public void setCalledSuccess(boolean calledSuccess) {
		this.calledSuccess = calledSuccess;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	

}
