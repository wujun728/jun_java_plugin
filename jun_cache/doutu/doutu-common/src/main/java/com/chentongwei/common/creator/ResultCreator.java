package com.chentongwei.common.creator;

import com.chentongwei.common.constant.ResponseEnum;
import com.chentongwei.common.entity.Result;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

/**
 * Result 工具类，封装返回结果给客户端
 * 
 * @author TongWei.Chen 2017-5-14 18:12:34
 */
public final class ResultCreator {
	
	/**
	 * 请求成功
	 * 
	 * @return
	 */
	public static final Result getSuccess() {
		return getResult(ResponseEnum.SUCCESS, null);
	}
	
	/**
	 * 请求成功
	 * 
	 * @param object 返回给客户端的结果
	 * @return
	 */
	public static final Result getSuccess(final Object object) {
		return getResult(ResponseEnum.SUCCESS, object);
	}

	/**
	 * 请求失败
	 *
	 * @return
	 */
	public static final Result getFail() {
		return getResult(ResponseEnum.FAILED, null);
	}

	/**
	 * 封装result
	 *
	 * @param responseEnum 通用状态枚举
	 * @param object 返回给客户端的结果
	 * @return
	 */
	private static final Result getResult(final ResponseEnum responseEnum, final Object object) {
		Result result = new Result();
		result.setCode(responseEnum.getCode());
		result.setMsg(responseEnum.getMsg());
		result.setResult(object);
		/**
		 * 由于pageHelper的Page继承了ArrayList，所以判断下是否是Page类型，
		 * 若是Page类型则证明需要分页，然后new PageInfo()返回PageHelper自带的分页信息给客户端，
		 * 比如：共多少页，是否有下一页等字段
		 */
		if(object instanceof Page) {
			@SuppressWarnings("unchecked")
			Page<Object> list = (Page<Object>) object;
			PageInfo<Object> ObjectPage = new PageInfo<>(list);
			result.setResult(ObjectPage);
		}
		return result;
	}
}