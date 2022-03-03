package net.chenlin.dp.common.utils;

import net.chenlin.dp.common.constant.MsgConstant;
import net.chenlin.dp.common.constant.SystemConstant;
import net.chenlin.dp.common.entity.R;

/**
 * 通用工具类
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年8月12日 下午1:34:44
 */
public class CommonUtils {

	/**
	 * 对象是否为空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		return false;
	}

	/**
	 * 判断整数是否大于零
	 * 
	 * @param number
	 * @return
	 */
	public static boolean isIntThanZero(int number) {
		if (number > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 新增，修改提示
	 * @param count
	 * @return
	 */
	public static R msg(int count) {
		if(isIntThanZero(count)){
			return R.ok(MsgConstant.MSG_OPERATION_SUCCESS);
		}
		return R.error(MsgConstant.MSG_OPERATION_FAILED);
	}
	
	/**
	 * 查询详情提示
	 * @param data
	 * @return
	 */
	public static R msg(Object data) {
		if(isNullOrEmpty(data)){
			return R.error(MsgConstant.MSG_INIT_FORM);
		}
		return R.ok().put(SystemConstant.DATA_ROWS, data);
	}
	
	/**
	 * 返回数据
	 * @param data
	 * @return
	 */
	public static R msgNotCheckNull(Object data) {
		return R.ok().put(SystemConstant.DATA_ROWS, data);
	}
	
	/**
	 * 删除提示
	 * @param total
	 * @param count
	 * @return
	 */
	public static R msg(Object[] total, int count) {
		if(total.length == count){
			return R.ok(MsgConstant.MSG_OPERATION_SUCCESS);
		}else{
			if(isIntThanZero(count)){
				return R.error(MsgConstant.removeFailed(total.length, count));
			}else{
				return R.error(MsgConstant.MSG_OPERATION_FAILED);
			}
		}
	}
	
}
