package com.itstyle.jwt.common.constant;
/**
 * 系统级静态变量
 * 创建者 科帮网 
 * 创建时间	2017年11月20日
 */
public class SystemConstant {
    /**
	 * token
	 */
	public static final int RESCODE_REFTOKEN_MSG = 1006;		//刷新TOKEN(有返回数据)
	public static final int RESCODE_REFTOKEN = 1007;			//刷新TOKEN
	
	public static final int JWT_ERRCODE_NULL = 4000;			//Token不存在
	public static final int JWT_ERRCODE_EXPIRE = 4001;			//Token过期
	public static final int JWT_ERRCODE_FAIL = 4002;			//验证不通过

	/**
	 * JWT
	 */
	public static final String JWT_SECERT = "8677df7fc3a34e26a61c034d5ec8245d";			//密匙
	public static final long JWT_TTL = 60 * 60 * 1000;									//token有效时间
}
