package com.diamond.wm.accesstoken.exception;

import org.json.JSONObject;

public class WechatRequestException extends Exception {


	JSONObject result;
	
	public WechatRequestException(JSONObject result) {
		this.result = result;
	}

	@Override
	public String getMessage() {
		if(result!=null){
			return result.toString();
		}
		return "";
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
