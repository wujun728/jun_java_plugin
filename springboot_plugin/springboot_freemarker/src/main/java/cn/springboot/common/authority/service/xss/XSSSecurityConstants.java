package cn.springboot.common.authority.service.xss;

public class XSSSecurityConstants {

	/**  
	 * 配置文件标签 isCheckHeader
	 */
	public static String IS_CHECK_HEADER = "isCheckHeader";

	/**
	 * 配置文件标签 isCheckParameter
	 */
	public static String IS_CHECK_PARAMETER = "isCheckParameter";

    /**
     * 配置文件标签 isCheckUrl
     */
    public static String IS_CHECK_URL = "isCheckUrl";

	/**
	 * 配置文件标签 isLog
	 */
	public static String IS_LOG = "isLog";

	/**
	 * 配置文件标签 isChain
	 */
	public static String IS_CHAIN = "isChain";

	/**
	 * 配置文件标签 replace
	 */
	public static String REPLACE = "replace";

    /**
     * 特殊url过滤标签  checkUrlList
     */
    public static String CHECK_URL_LIST = "checkUrlList";
    public static String CHECK_URL_URL = "url";
    public static String CHECK_URL_REGEX = "regex";

	/**
	 * 配置文件标签 regexList
	 */
	public static String REGEX_LIST = "regexList";

	/**
	 * 替换非法字符的字符串
	 */
	public static String REPLACEMENT = "";

	/**
	 * FILTER_ERROR_PAGE:过滤后错误页面
	 */
	public static String FILTER_ERROR_PAGE = "/error";

}
