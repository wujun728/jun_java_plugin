package com.chentongwei.common.creator;

import com.chentongwei.common.enums.IBaseEnum;
import com.chentongwei.common.enums.msg.ResponseEnum;
import com.chentongwei.common.entity.Result;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: Result 工具类，封装返回结果给客户端
 */
public final class ResultCreator {
	
	/**
	 * 请求成功
	 * 
	 * @return
	 */
	public static final Result getSuccess() {
		return successResult(ResponseEnum.SUCCESS, null);
	}

	/**
	 * 请求成功
	 *
	 * @param object 返回给客户端的结果
	 * @return
	 */
	public static final Result getSuccess(final Object object) {
		return successResult(ResponseEnum.SUCCESS, object);
	}

	/**
	 * 请求失败
	 *
	 * @return
	 */
	public static final Result getFail() {
		return failResult(ResponseEnum.FAILED, null);
	}

	/**
	 * 请求成功
	 *
	 * @param baseEnum 返回给客户端的结果
	 * @return
	 */
	public static final Result getFail(final IBaseEnum baseEnum, final Object object) {
		return failResult(baseEnum, object);
	}

	/**
	 * 封装失败result
	 *
	 * @param baseEnum：通用状态枚举
	 * @param object：对象
	 * @return
	 */
	private static final Result failResult(final IBaseEnum baseEnum, final Object object) {
		Result result = new Result();
		result.setCode(baseEnum.getCode());
		result.setMsg(baseEnum.getMsg());
		result.setResult(object);
		return result;
	}

	/**
	 * 封装成功result
	 *
	 * @param baseEnum：通用状态枚举
	 * @param object：返回给客户端的结果
	 * @return
	 */
	private static final Result successResult(final IBaseEnum baseEnum, final Object object) {
		Result result = new Result();
		result.setCode(baseEnum.getCode());
		result.setMsg(baseEnum.getMsg());
		result.setResult(object);
		/**
		 * 由于pageHelper的Page继承了ArrayList，所以判断下是否是Page类型，
		 * 若是Page类型则证明需要分页，然后new PageInfo()返回PageHelper自带的分页信息给客户端，
		 * 比如：共多少页，是否有下一页等字段
		 */
		if(object instanceof Page) {
			@SuppressWarnings("unchecked")
			Page<Object> list = (Page<Object>) object;
			PageInfo<Object> objectPage = new PageInfo<>(list);
			result.setResult(objectPage);
		}
		return result;
	}
}