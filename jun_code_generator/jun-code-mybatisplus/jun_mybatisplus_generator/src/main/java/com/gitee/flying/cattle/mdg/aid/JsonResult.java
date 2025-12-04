/**   
 * Copyright © 2019 dream horse Info. Tech Ltd. All rights reserved.
 * @Package: com.gitee.flying.cattle.mdg.aid
 * @author: flying-cattle  
 * @date: 2019年4月9日 下午8:15:25 
 */
package com.gitee.flying.cattle.mdg.aid;

import java.io.Serializable;
import java.net.ConnectException;
import java.sql.SQLException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**   
 * Copyright: Copyright (c) 2019 
 * 
 * <p>说明： 用户服务层</P>
 * @version: V1.0
 * @author: flying-cattle
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonResult<T> implements Serializable{
	
	private static final long serialVersionUID = 1071681926787951549L;

    /**
     *<p>状态码</p>
     */
    private String code;
    /**
     * <p>业务码</p>
     */
    private String operate;
    /**
     *<p> 状态说明</p>
     */
    private String message;
    /**
     * <p>返回数据</p>
     */
    private T data;
   
    /**
     * <p>返回成功,有数据</p>
     * @param message 操作说明
     * @param data 对象
     * @return JsonResult
     */
    public JsonResult<T> success(String message,T data) {
    	this.setCode(Const.CODE_SUCCESS);
    	this.setOperate(Const.OPERATE_SUCCESS);
    	this.setMessage(message);
    	this.setData(data);
    	return this;
    }

    /**
     * <p>返回成功,有数据</p>
     * @param data 对象
     * @return JsonResult
     */
    public JsonResult<T> success(T data) {
    	this.setCode(Const.CODE_SUCCESS);
    	this.setOperate(Const.OPERATE_SUCCESS);
    	this.setMessage("操作成功");
    	this.setData(data);
    	return this;
    }
    /**
     * <p>返回成功,无数据</p>
     * @param message 操作说明
     * @return JsonResult
     */
    public JsonResult<T> success(String message) {
    	this.setCode(Const.CODE_SUCCESS);
    	this.setOperate(Const.OPERATE_SUCCESS);
    	this.setMessage(message);
    	this.setData(null);
    	return this;

    }
    /**
     * <p>返回失败,无数据</p>
     * @param message 消息
     * @return JsonResult
     */
    public JsonResult<T> error(String message) {
    	this.setCode(Const.CODE_FAILED);
    	this.setOperate(Const.OPERATE_FAILED);
    	this.setMessage(message);
    	this.setData(null);
    	return this;
    }
    /**
     * <p>返回失败,有数据</p>
     * @param message 消息
     * @param data 对象
     * @return JsonResult
     */
    public JsonResult<T> error(String message,T data) {
    	this.setCode(Const.CODE_FAILED);
    	this.setOperate(Const.OPERATE_FAILED);
    	this.setMessage(message);
    	this.setData(data);
    	return this;
    }
    public JsonResult(Throwable throwable) {
    	this.operate=Const.OPERATE_FAILED;
        if(throwable instanceof NullPointerException){
            this.code= "1001";
            this.message="空指针："+throwable;
        }else if(throwable instanceof ClassCastException ){
            this.code= "1002";
            this.message="类型强制转换异常："+throwable;
        }else if(throwable instanceof ConnectException){
            this.code= "1003";
            this.message="链接失败："+throwable;
        }else if(throwable instanceof IllegalArgumentException ){
            this.code= "1004";
            this.message="传递非法参数异常："+throwable;
        }else if(throwable instanceof NumberFormatException){
            this.code= "1005";
            this.message="数字格式异常："+throwable;
        }else if(throwable instanceof IndexOutOfBoundsException){
            this.code= "1006";
            this.message="下标越界异常："+throwable;
        }else if(throwable instanceof SecurityException){
            this.code= "1007";
            this.message="安全异常："+throwable;
        }else if(throwable instanceof SQLException){
            this.code= "1008";
            this.message="数据库异常："+throwable;
        }else if(throwable instanceof ArithmeticException){
            this.code= "1009";
            this.message="算术运算异常："+throwable;
        }else if(throwable instanceof RuntimeException){
            this.code= "1010";
            this.message="运行时异常："+throwable;
        }else if(throwable instanceof Exception){ 
            this.code= "9999";
            this.message="未知异常"+throwable;
        }
    }
}

