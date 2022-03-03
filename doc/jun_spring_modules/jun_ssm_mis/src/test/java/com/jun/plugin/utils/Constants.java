package com.jun.plugin.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hibernate.Session;

import com.erp.model.Log;
import com.erp.shiro.ShiroUser;
import com.jun.plugin.utils.biz.PageUtil;

public class Constants {
	

	public static final String LOGIN_SESSION_DATANAME = "users";
	public static final String LOGIN_URL = "login";
	public static final String LOGIN_SUCCESS_URL = "index";
	public static final String LOGIN_LOGIN_OUT_URL = "loginout";
	public static final String LOGIN_MSG = "loginMsg";
	public static final String USERNAME_IS_NULL = "用户名为空!";
	public static final String LOGIN_IS_EXIST = "该用户已登录!";
	public static final String UNKNOWN_SESSION_EXCEPTION = "异常会话!";
	public static final String UNKNOWN_ACCOUNT_EXCEPTION = "账号错误!";
	public static final String INCORRECT_CREDENTIALS_EXCEPTION = "密码错误!";
	public static final String LOCKED_ACCOUNT_EXCEPTION = "账号已被锁定，请与系统管理员联系!";
	public static final String INCORRECT_CAPTCHA_EXCEPTION = "验证码错误!";
	public static final String AUTHENTICATION_EXCEPTION = "您没有授权!";
	public static final String UNKNOWN_EXCEPTION = "出现未知异常,请与系统管理员联系!";
	public static final String TREE_GRID_ADD_STATUS = "add";
	public static final String POST_DATA_SUCCESS = "数据更新成功!";
	public static final String POST_DATA_FAIL = "提交失败了!";
	public static final String GET_SQL_LIKE = "%";
	public static final String IS_FUNCTION = "F";
	public static final String PERSISTENCE_STATUS = "A";
	public static final String PERSISTENCE_DELETE_STATUS = "I";
	public static final String SYSTEM_ADMINISTRATOR = "admin";
	public static final String NULL_STRING = "";
	public static final String IS_DOT = ".";
	public static final String HQL_LIKE = "like";
	public static final String TEXT_TYPE_PLAIN = "text/plain";
	public static final String TEXT_TYPE_HTML = "text/html";
	public static final String FUNCTION_TYPE_O = "O";
	public static final String TREE_STATUS_OPEN = "open";
	public static final String TREE_STATUS_CLOSED = "closed";
	public static final String IS_EXT_SUBMENU = " 或可能包含菜单!";
	public static final String SHIRO_USER = "shiroUser";
	public static final String LOGS_INSERT = "insert:";
	public static final String LOGS_INSERT_TEXT = "插入:";
	public static final String LOGS_INSERT_NAME = "insertLogs";
	public static final String LOGS_UPDATE = "update:";
	public static final String LOGS_UPDATE_TEXT = "更新:";
	public static final String LOGS_UPDATE_NAME = "updateLogs";
	public static final String LOGS_DELETE = "delete:";
	public static final String LOGS_DELETE_TEXT = "删除:";
	public static final String LOGS_DELETE_NAME = "deleteLogs";
	public static final String LOGS_TB_NAME = "Log";
	public static final String FILE_SUFFIX_SQL = ".sql";
	public static final String FILE_SUFFIX_ZIP = ".zip";

	/**
	 * 函数功能说明 TODO:获取UUID生成的主键 Administrator修改者名字 2013-5-8修改日期 修改内容
	 * 
	 * @Title: getPrimaryKeyByUUID
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getPrimaryKeyByUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 函数功能说明 TODO:获取当前登录用户实体类 Administrator修改者名字 2013-5-10修改日期 修改内容
	 * 
	 * @Title: getCurrendUser
	 * @Description: TODO:
	 * @param @return 设定文件
	 * @return Users 返回类型
	 * @throws
	 */
	public static ShiroUser getCurrendUser() {
		Subject subject = SecurityUtils.getSubject();
		return (ShiroUser) subject.getSession().getAttribute(SHIRO_USER);
	}

	/**
	 * 函数功能说明 TODO:高级查询hql条件拼接 Administrator修改者名字 2013-5-30修改日期 修改内容
	 * 
	 * @Title: getSearchConditionsHQL
	 * @Description:
	 * @param @param asName
	 * @param @param searchColumnNames
	 * @param @param searchAnds
	 * @param @param searchConditions
	 * @param @param searchVals
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getGradeSearchConditionsHQL(String asName, PageUtil pageUtil) {
		String searchAnds = pageUtil.getSearchAnds();
		String searchColumnNames = pageUtil.getSearchColumnNames();
		String searchConditions = pageUtil.getSearchConditions();
		String searchVals = pageUtil.getSearchVals();
		if (null != searchColumnNames && searchColumnNames.trim().length() > 0) {
			StringBuffer sb = new StringBuffer();
			String[] searchColumnNameArray = searchColumnNames.split("\\,");
			String[] searchAndsArray = searchAnds.split("\\,");
			String[] searchConditionsArray = searchConditions.split("\\,");
			String[] searchValsArray = searchVals.split("\\,");
			for (int i = 0; i < searchColumnNameArray.length; i++) {
				if (searchColumnNameArray[i].trim().length() > 0 && searchConditionsArray[i].trim().length() > 0) {
					/*
					 * if (i == 0) { sb.append(asName+"."+searchColumnNameArray[i].trim() + " " + searchConditionsArray[i].trim() + " " + searchValsArray[i].trim()); } else { }
					 */
					String temp = searchValsArray[i].trim().replaceAll("\\'", "");
					if (HQL_LIKE.equals(searchConditionsArray[i].trim())) {
						sb.append(" " + searchAndsArray[i].trim() + " " + asName + IS_DOT + searchColumnNameArray[i].trim() + " " + searchConditionsArray[i].trim() + " " + "'%"
								+ temp + "%'");

					} else {
						sb.append(" " + searchAndsArray[i].trim() + " " + asName + IS_DOT + searchColumnNameArray[i].trim() + " " + searchConditionsArray[i].trim() + " " + "'"
								+ temp + "'");
					}
				}
			}
			if (sb.length() > 0) {
				return sb.toString();
			}
		}
		return NULL_STRING;
	}
	
	/**
	 * 函数功能说明 TODO:高级查询hql条件拼接 Administrator修改者名字 2013-5-30修改日期 修改内容
	 * 
	 * @Title: getSearchConditionsHQL
	 * @Description:
	 * @param @param asName
	 * @param @param searchColumnNames
	 * @param @param searchAnds
	 * @param @param searchConditions
	 * @param @param searchVals
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getLogSortOrderHql(String asName, String desc) {
		return " order by "+asName+".logDate "+desc;
	}

	/**
	 * 函数功能说明 TODO:获得简单查询条件 Administrator修改者名字 2013-5-30修改日期 修改内容
	 * 
	 * @Title: getSearchConditionsHQL
	 * @Description:
	 * @param @param asName
	 * @param @param param
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getSearchConditionsHQL(String asName, Map<String, Object> param) {
		StringBuffer sb = new StringBuffer();
		if (param != null && !param.isEmpty()) {
			for (String name : param.keySet()) {
				sb.append(" and " + asName + Constants.IS_DOT + name + " like :" + name + "");
			}
		}
		return sb.toString();
	}

	/**
	 * 函数功能说明 TODO:获取操作日志 Administrator修改者名字 2013-6-18修改日期 修改内容
	 * 
	 * @Title: getLogs
	 * @Description:
	 * @param @param session
	 * @param @param o
	 * @param @param eventName
	 * @param @param er
	 * @param @param name 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static <T> void getLogs(Session session, T o, String eventName, String er, String name) {
		if (!LOGS_TB_NAME.equals(o.getClass().getSimpleName())) {
			String ip = getIpAddr();
			String mac = getMacAddr();
			String[] sdf = getFiledName(o);
			String id = getFieldValueByName(sdf[1], o).toString();
			Log l = new Log();
			l.setUserId(Constants.getCurrendUser().getUserId());
			l.setName(Constants.getCurrendUser().getAccount());
			l.setLogDate(new Date());
			l.setEventName(eventName + o.getClass().getSimpleName());
			l.setEventRecord(er + o.getClass().getName());
			l.setObjectId(id);
			l.setType(2);
			l.setIp(ip);
			l.setMac(mac);
			session.save(l);
		}
	}

	public static String[] getFiledName(Object o) {
		try {
			Field[] fields = o.getClass().getDeclaredFields();
			String[] fieldNames = new String[fields.length];
			for (int i = 0; i < fields.length; i++) {
				fieldNames[i] = fields[i].getName();
			}
			return fieldNames;
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object getFieldValueByName(String fieldName, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});
			return value;
		} catch (Exception e) {
			System.out.println("属性不存在");
			return "";
		}
	}

	public static HashMap<String, Method> ConverBean(Class<?> drbean) {
		Class<?> stopClass = null;
		// 存放class信息
		BeanInfo drbeaninfo = null;
		// 存放属性信息
		PropertyDescriptor[] props;
		HashMap<String, Method> map = new HashMap<String, Method>();
		try {
			// 获取class中得属性方法信息
			drbeaninfo = Introspector.getBeanInfo(drbean, stopClass);
			// 把class中属性放入PropertyDescriptor数组中
			props = drbeaninfo.getPropertyDescriptors();
			for (int i = 0; i < props.length; i++) {
				// 获取属性所对应的set方法
				Method setMethod = props[i].getWriteMethod();
				// 判断属性是否有set方法 如果有放入map<属性名，set方法>中
				if (setMethod != null) {
					String field = props[i].getName().toLowerCase();
					map.put(field, setMethod);
				}
			}
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 函数功能说明 TODO:获取客户端ip地址 Administrator修改者名字 2013-6-19修改日期 修改内容
	 * 
	 * @Title: getIpAddr
	 * @Description:
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getIpAddr() {
		// HttpServletRequest request=ServletActionContext.getRequest();
		// String ip = request.getHeader("x-forwarded-for");
		// if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		// ip = request.getHeader("Proxy-Client-IP");
		// }
		// if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		// ip = request.getHeader("WL-Proxy-Client-IP");
		// }
		// if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		// ip = request.getRemoteAddr();
		// }
		// return ip;
		return "null";
	}

	/**
	 * 函数功能说明 TODO:获取客户端mac地址 Administrator修改者名字 2013-6-19修改日期 修改内容
	 * 
	 * @Title: getMacAddr
	 * @Description:
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getMacAddr() {
		// String smac = "";
		// try
		// {
		// UdpGetClientMacAddr umac = new UdpGetClientMacAddr(getIpAddr(request));
		// smac = umac.GetRemoteMacAddr();
		// } catch (Exception e)
		// {
		// e.printStackTrace();
		// }
		// return smac;
		return "null";
	}

	private static final int BUFFER_SIZE = 16 * 1024;

	public static void copy(File src, String fullSavePath) {
		InputStream in = null;
		OutputStream out = null;
		File newFile = new File(fullSavePath);
		try {
			in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(newFile), BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String BASE_PATH = System.getProperty("erp");

	public static String dbBackUp() {
		// 生成临时备份文件
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
		String fineName = "dbBackUp-" + sd.format(new Date());
		String sqlName = fineName + Constants.FILE_SUFFIX_SQL;
		String pathSql = BASE_PATH + "attachment" + File.separator + "dbBackUp";
		try {
			File filePathSql = new File(pathSql);
			if (!filePathSql.exists()) {
				filePathSql.mkdir();
			}
			StringBuffer sbs = new StringBuffer();
			sbs.append("mysqldump ");
			sbs.append("-h ");
			// sbs.append(" 192.168.110.10 ");
			sbs.append(" 127.0.0.1 ");
			sbs.append("--user=root");
			sbs.append(" --password=mysqladmin");
			sbs.append(" --lock-all-tables=true ");
			sbs.append("--result-file=" + pathSql + File.separator);
			sbs.append(sqlName + " ");
			sbs.append(" --default-character-set=utf8 ");
			sbs.append("ERP");
			Runtime runtime = Runtime.getRuntime();
			Process child = runtime.exec(sbs.toString());
			// 读取备份数据并生成临时文件
			InputStream in = child.getInputStream();
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(pathSql), "utf8");
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf8"));
			String line = reader.readLine();
			while (line != null) {
				writer.write(line + "\n");
				line = reader.readLine();
			}
			writer.flush();
		} catch (Exception e) {

		}
		return sqlName;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private Constants() {
	}

	public static final int MSSQLSERVER = 0;
	public static final int MYSQL = 1;
	public static final int ORACLE = 2;

	public static final int TOPIC_PAGE_SIZE = 5;

	public static final String PRV_CHECK_TYPE = "prv";
	public static final String DEFAULT_CHECK_TYPE = "default";

	public static final String CHECK_TREE_TYPE = "checkbox";
	public static final String DEFAULT_TREE_TYPE = "default";
	public static final String RADIO_TREE_TYPE = "radio";
	public static final String COMPOSITE_TREE_TYPE = "composite";
	@SuppressWarnings("unused")
	private String treeType = DEFAULT_TREE_TYPE;

	public final static String PIC_ROOT = "/pic/";

	public final static String SOFT_ROOT = "/soft/";

	public final static String ATTACH_ROOT = "/attach/";

	public final static String ProgramPath = "/programs/";

	public final static long MAX_UPLOAD_PIC_SIZE = 1000 * 1024 * 4;

	public final static long MAX_UPLOAD_FILE_SIZE = 10 * 1024 * 1024 * 8;

	public final static long MAX_UPLOAD_SOFT_SIZE = 100 * 1024 * 1024 * 8;

	public final static int MAX_LOGIN_TIMES = 3;

	public final static int LOCK_TIME = +10;

	public final static String[] ADMIN = { "admin", "longlob" };
	
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/

	
	// 货币种类（人民币、美元、欧元、英磅、加拿大元、澳元、卢布、港币、新台币、韩元、新加坡元、新西兰元、日元、马元、瑞士法郎、瑞典克朗、丹麦克朗、兹罗提、挪威克朗、福林、捷克克朗、葡币）
	public enum CurrencyType {
		CNY, USD, EUR, GBP, CAD, AUD, RUB, HKD, TWD, KRW, SGD, NZD, JPY, MYR, CHF, SEK, DKK, PLZ, NOK, HUF, CSK, MOP
	};
	
	// 小数位精确方式（四舍五入、向上取整、向下取整）
	public enum RoundType {
		roundHalfUp, roundUp, roundDown
	}
	
	// 库存预占时间点（下订单、订单付款、订单发货）
	public enum StoreFreezeTime {
		order, payment, ship
	}
	
	// 水印位置（无、左上、右上、居中、左下、右下）
	public enum WatermarkPosition {
		no, topLeft, topRight, center, bottomLeft, bottomRight
	}
	
	// 积分获取方式（禁用积分获取、按订单总额计算、为商品单独设置）
	public enum PointType {
		disable, orderAmount, productSet
	}

	public static final String HOT_SEARCH_SEPARATOR = ",";// 热门搜索分隔符
	public static final String EXTENSION_SEPARATOR = ",";// 文件扩展名分隔符

	public static final String LOGO_UPLOAD_NAME = "logo";// Logo图片文件名称(不包含扩张名)
	public static final String DEFAULT_BIG_PRODUCT_IMAGE_FILE_NAME = "default_big_product_image";// 默认商品图片（大）文件名称(不包含扩展名)
	public static final String DEFAULT_SMALL_PRODUCT_IMAGE_FILE_NAME = "default_small_product_image";// 默认商品图片（小）文件名称(不包含扩展名)
	public static final String DEFAULT_THUMBNAIL_PRODUCT_IMAGE_FILE_NAME = "default_thumbnail_product_image";// 商品缩略图文件名称(不包含扩展名)
	public static final String WATERMARK_IMAGE_FILE_NAME = "watermark";// 水印图片文件名称(不包含扩展名)
	public static final String UPLOAD_IMAGE_DIR = "/upload/image/";// 图片文件上传目录
	public static final String UPLOAD_MEDIA_DIR = "/upload/media/";// 媒体文件上传目录
	public static final String UPLOAD_FILE_DIR = "/upload/file/";// 其它文件上传目录

	private String systemName;// 系统名称
	private String systemVersion;// 系统版本
	private String systemDescription;// 系统描述
	private Boolean isInstalled;// 是否已安装

	private String shopName;// 网店名称
	private String shopUrl;// 网店网址
	private String shopLogo;// 网店Logo
	private String hotSearch;// 热门搜索关键词

	private String metaKeywords;// 首页页面关键词
	private String metaDescription;// 首页页面描述

	private String address;// 联系地址
	private String phone;// 联系电话
	private String zipCode;// 邮编
	private String email;// 联系email
	
	private CurrencyType currencyType;// 货币种类
	private String currencySign;// 货币符号
	private String currencyUnit;// 货币单位

	private Integer priceScale;// 商品价格精确位数
	private RoundType priceRoundType;// 商品价格精确方式
	private Integer orderScale;// 订单金额精确位数
	private RoundType orderRoundType;// 订单金额精确方式

	private String certtext;// 备案号
	private Integer storeAlertCount;// 库存报警数量
	private StoreFreezeTime storeFreezeTime;// 库存预占时间点
	private Integer uploadLimit;// 文件上传最大值,0表示无限制,单位KB
	private Boolean isLoginFailureLock; // 是否开启登录失败锁定账号功能
	private Integer loginFailureLockCount;// 同一账号允许连续登录失败的最大次数，超出次数后将锁定其账号
	private Integer loginFailureLockTime;// 账号锁定时间(单位：分钟,0表示永久锁定)
	private Boolean isRegister;// 是否开放注册

	private String watermarkImagePath; // 水印图片路径
	private WatermarkPosition watermarkPosition; // 水印位置
	private Integer watermarkAlpha;// 水印透明度

	private Integer bigProductImageWidth;// 商品图片（大）宽度
	private Integer bigProductImageHeight;// 商品图片（大）高度
	private Integer smallProductImageWidth;// 商品图片（小）宽度
	private Integer smallProductImageHeight;// 商品图片（小）高度
	private Integer thumbnailProductImageWidth;// 商品缩略图宽度
	private Integer thumbnailProductImageHeight;// 商品缩略图高度

	private String defaultBigProductImagePath;// 默认商品图片（大）
	private String defaultSmallProductImagePath;// 默认商品图片（小）
	private String defaultThumbnailProductImagePath;// 默认缩略图

	private String allowedUploadImageExtension;// 允许上传的图片文件扩展名（为空表示不允许上传图片文件）
	private String allowedUploadMediaExtension;// 允许上传的媒体文件扩展名（为空表示不允许上传媒体文件）
	private String allowedUploadFileExtension;// 允许上传的文件扩展名（为空表示不允许上传文件）
	
	private String smtpFromMail;// 发件人邮箱
	private String smtpHost;// SMTP服务器地址
	private Integer smtpPort;// SMTP服务器端口
	private String smtpUsername;// SMTP用户名
	private String smtpPassword;// SMTP密码
	
	private PointType pointType;// 积分获取方式
	private Double pointScale;// 积分换算比率

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

	public String getSystemDescription() {
		return systemDescription;
	}

	public void setSystemDescription(String systemDescription) {
		this.systemDescription = systemDescription;
	}

	public Boolean getIsInstalled() {
		return isInstalled;
	}

	public void setIsInstalled(Boolean isInstalled) {
		this.isInstalled = isInstalled;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopUrl() {
		return shopUrl;
	}

	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}

	public String getShopLogo() {
		return shopLogo;
	}

	public void setShopLogo(String shopLogo) {
		this.shopLogo = shopLogo;
	}

	public String getHotSearch() {
		return hotSearch;
	}

	public void setHotSearch(String hotSearch) {
		this.hotSearch = hotSearch;
	}

	public String getMetaKeywords() {
		return metaKeywords;
	}

	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public CurrencyType getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(CurrencyType currencyType) {
		this.currencyType = currencyType;
	}

	public String getCurrencySign() {
		return currencySign;
	}

	public void setCurrencySign(String currencySign) {
		this.currencySign = currencySign;
	}

	public String getCurrencyUnit() {
		return currencyUnit;
	}

	public void setCurrencyUnit(String currencyUnit) {
		this.currencyUnit = currencyUnit;
	}

	public Integer getPriceScale() {
		return priceScale;
	}

	public void setPriceScale(Integer priceScale) {
		this.priceScale = priceScale;
	}

	public RoundType getPriceRoundType() {
		return priceRoundType;
	}

	public void setPriceRoundType(RoundType priceRoundType) {
		this.priceRoundType = priceRoundType;
	}

	public Integer getOrderScale() {
		return orderScale;
	}

	public void setOrderScale(Integer orderScale) {
		this.orderScale = orderScale;
	}

	public RoundType getOrderRoundType() {
		return orderRoundType;
	}

	public void setOrderRoundType(RoundType orderRoundType) {
		this.orderRoundType = orderRoundType;
	}

	public String getCerttext() {
		return certtext;
	}

	public void setCerttext(String certtext) {
		this.certtext = certtext;
	}

	public Integer getStoreAlertCount() {
		return storeAlertCount;
	}

	public void setStoreAlertCount(Integer storeAlertCount) {
		this.storeAlertCount = storeAlertCount;
	}

	public StoreFreezeTime getStoreFreezeTime() {
		return storeFreezeTime;
	}

	public void setStoreFreezeTime(StoreFreezeTime storeFreezeTime) {
		this.storeFreezeTime = storeFreezeTime;
	}

	public Integer getUploadLimit() {
		return uploadLimit;
	}

	public void setUploadLimit(Integer uploadLimit) {
		this.uploadLimit = uploadLimit;
	}

	public Boolean getIsLoginFailureLock() {
		return isLoginFailureLock;
	}

	public void setIsLoginFailureLock(Boolean isLoginFailureLock) {
		this.isLoginFailureLock = isLoginFailureLock;
	}

	public Integer getLoginFailureLockCount() {
		return loginFailureLockCount;
	}

	public void setLoginFailureLockCount(Integer loginFailureLockCount) {
		this.loginFailureLockCount = loginFailureLockCount;
	}

	public Integer getLoginFailureLockTime() {
		return loginFailureLockTime;
	}

	public void setLoginFailureLockTime(Integer loginFailureLockTime) {
		this.loginFailureLockTime = loginFailureLockTime;
	}

	public Boolean getIsRegister() {
		return isRegister;
	}

	public void setIsRegister(Boolean isRegister) {
		this.isRegister = isRegister;
	}

	public String getWatermarkImagePath() {
		return watermarkImagePath;
	}

	public void setWatermarkImagePath(String watermarkImagePath) {
		this.watermarkImagePath = watermarkImagePath;
	}

	public WatermarkPosition getWatermarkPosition() {
		return watermarkPosition;
	}

	public void setWatermarkPosition(WatermarkPosition watermarkPosition) {
		this.watermarkPosition = watermarkPosition;
	}

	public Integer getWatermarkAlpha() {
		return watermarkAlpha;
	}

	public void setWatermarkAlpha(Integer watermarkAlpha) {
		this.watermarkAlpha = watermarkAlpha;
	}

	public Integer getBigProductImageWidth() {
		return bigProductImageWidth;
	}

	public void setBigProductImageWidth(Integer bigProductImageWidth) {
		this.bigProductImageWidth = bigProductImageWidth;
	}

	public Integer getBigProductImageHeight() {
		return bigProductImageHeight;
	}

	public void setBigProductImageHeight(Integer bigProductImageHeight) {
		this.bigProductImageHeight = bigProductImageHeight;
	}

	public Integer getSmallProductImageWidth() {
		return smallProductImageWidth;
	}

	public void setSmallProductImageWidth(Integer smallProductImageWidth) {
		this.smallProductImageWidth = smallProductImageWidth;
	}

	public Integer getSmallProductImageHeight() {
		return smallProductImageHeight;
	}

	public void setSmallProductImageHeight(Integer smallProductImageHeight) {
		this.smallProductImageHeight = smallProductImageHeight;
	}

	public Integer getThumbnailProductImageWidth() {
		return thumbnailProductImageWidth;
	}

	public void setThumbnailProductImageWidth(Integer thumbnailProductImageWidth) {
		this.thumbnailProductImageWidth = thumbnailProductImageWidth;
	}

	public Integer getThumbnailProductImageHeight() {
		return thumbnailProductImageHeight;
	}

	public void setThumbnailProductImageHeight(Integer thumbnailProductImageHeight) {
		this.thumbnailProductImageHeight = thumbnailProductImageHeight;
	}

	public String getDefaultBigProductImagePath() {
		return defaultBigProductImagePath;
	}

	public void setDefaultBigProductImagePath(String defaultBigProductImagePath) {
		this.defaultBigProductImagePath = defaultBigProductImagePath;
	}

	public String getDefaultSmallProductImagePath() {
		return defaultSmallProductImagePath;
	}

	public void setDefaultSmallProductImagePath(String defaultSmallProductImagePath) {
		this.defaultSmallProductImagePath = defaultSmallProductImagePath;
	}

	public String getDefaultThumbnailProductImagePath() {
		return defaultThumbnailProductImagePath;
	}

	public void setDefaultThumbnailProductImagePath(String defaultThumbnailProductImagePath) {
		this.defaultThumbnailProductImagePath = defaultThumbnailProductImagePath;
	}

	public String getAllowedUploadImageExtension() {
		return allowedUploadImageExtension;
	}

	public void setAllowedUploadImageExtension(String allowedUploadImageExtension) {
		this.allowedUploadImageExtension = allowedUploadImageExtension;
	}

	public String getAllowedUploadMediaExtension() {
		return allowedUploadMediaExtension;
	}

	public void setAllowedUploadMediaExtension(String allowedUploadMediaExtension) {
		this.allowedUploadMediaExtension = allowedUploadMediaExtension;
	}

	public String getAllowedUploadFileExtension() {
		return allowedUploadFileExtension;
	}

	public void setAllowedUploadFileExtension(String allowedUploadFileExtension) {
		this.allowedUploadFileExtension = allowedUploadFileExtension;
	}

	public String getSmtpFromMail() {
		return smtpFromMail;
	}

	public void setSmtpFromMail(String smtpFromMail) {
		this.smtpFromMail = smtpFromMail;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public Integer getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(Integer smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getSmtpUsername() {
		return smtpUsername;
	}

	public void setSmtpUsername(String smtpUsername) {
		this.smtpUsername = smtpUsername;
	}

	public String getSmtpPassword() {
		return smtpPassword;
	}

	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

	public PointType getPointType() {
		return pointType;
	}

	public void setPointType(PointType pointType) {
		this.pointType = pointType;
	}

	public Double getPointScale() {
		return pointScale;
	}

	public void setPointScale(Double pointScale) {
		this.pointScale = pointScale;
	}

//	// 获取热门搜索关键词集合
//	public List<String> getHotSearchList() {
//		return StringUtils.isNotEmpty(hotSearch) ? Arrays.asList(hotSearch.split(HOT_SEARCH_SEPARATOR)) : new ArrayList<String>();
//	}
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/**
	 * 开发模式
	 */
	public static final String MODE_DEVELOP = "DEV";
	
	/**
	 * 发布模式
	 */
	public static final String MODE_PUBLISH = "PUB";
	
	/**
	 * 删除：1
	 */
	public static final int DELETE_FLG_1 = 1;
	/**
	 * 删除：0『正常』
	 */
	public static final int DELETE_FLG_0 = 0;
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/**权限类型 01：菜单权限 */
	public static final String TAUTH_CTYPE_01 = "01";
	
	/**权限类型：02：按钮权限*/
	public static final String TAUTH_CTYPE_02 = "02";
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/**
	 * 
	 * @Title: getMacAddr
	 * @Description:
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static final String PAGE = "page";
	public static final int PAGE_NUM = 1;
	public static final String ROWS = "rows";
	public static final int ROWS_NUM = 10;
	public static final String METHOD = "method";
	public static final String STATUS = "status";
	@SuppressWarnings("unchecked")
	public static Map mapConfig(Map map) {
//		{page=1, queryForJson=, rows=10 method=queryForJson&status=queryLogsList}
		StringBuilder sql=new StringBuilder();
		if(null==map.get(Constants.ROWS)){
			map.put(Constants.ROWS, Constants.ROWS_NUM);
		}
		if(null==map.get(Constants.PAGE)){
			map.put(Constants.PAGE, Constants.PAGE_NUM);
		}
		if(null==map.get("method")||null==map.get("status")){
			return null;
		}
		if("queryForJson".equalsIgnoreCase(String.valueOf(map.get("method")))){
			if("queryLogsList".equalsIgnoreCase(String.valueOf(map.get("status")))){
				sql.append(" SELECT  *");
				sql.append(" FROM ");
				sql.append(" SYS_LOG");
				sql.append(" WHERE 1=1 ");
				sql.append(" ORDER BY LOG_DATE");
				sql.append(" DESC");
				sql.append(" LIMIT " + (Constants.PAGE_NUM - 1) * Constants.ROWS_NUM + "," + Constants.PAGE_NUM * Constants.ROWS_NUM);
				map.put("sql", sql.toString());
			}
		}
		
		
//		map.
		return map;
	}
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
	/********************************************************************************************************/
}
