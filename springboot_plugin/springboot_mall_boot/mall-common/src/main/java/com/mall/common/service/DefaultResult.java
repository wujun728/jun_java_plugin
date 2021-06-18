package com.mall.common.service;


import com.mall.common.utils.ResultCode;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回结果接口实现类
 * @author Wujun
 * @version 1.0
 * @create_at 2017年6月13日 下午9:36:35
 */
public class DefaultResult implements Result {

	private final Map<String,Object> map = new HashMap<String, Object>();

	private boolean success = true;

	private ResultCode resultCode;

	/**
	 * 测试结果是否成功
	 */
	@Override
	public boolean isSuccess() {
		return success;
	}

	public DefaultResult() {

	}

	public DefaultResult(boolean success) {
		if (success) {
			this.resultCode = new ResultCode(ResultCode.SUCCESS);
		} else {
			this.resultCode = new ResultCode(ResultCode.FAIL);
		}
	}

    public DefaultResult(ResultCode resultCode) {
    	this.resultCode = resultCode;
    	if (resultCode.code == ResultCode.SUCCESS) {
			success = true;
		} else {
			success = false;
		}
    }

	@Override
	public void setModel(String key, Object value) {
		map.put(key, value);
	}

	@Override
	public void setDefaultModel(Object model) {
		map.put(DEFAULT_MODEL_KEY, model);
	}

	@Override
	public void setResultCode(ResultCode resultCode) {
		this.resultCode = resultCode;
		if (resultCode.code == ResultCode.SUCCESS) {
			success = true;
		} else {
			success = false;
		}
	}

	@Override
	public ResultCode getResultCode() {
		return resultCode;
	}

	@Override
	public Map<String, Object> getModels() {
		return map;
	}

	/**
	 * 取得model对象
	 */
	@Override
	public Object getDefaultModel() {
		return map.get(DEFAULT_MODEL_KEY);
	}

	@Override
	public Object getModel(String key) {
		return getModels().get(key);
	}
}
