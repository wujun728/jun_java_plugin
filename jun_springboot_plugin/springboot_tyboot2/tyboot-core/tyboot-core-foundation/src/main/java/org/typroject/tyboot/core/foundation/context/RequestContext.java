package org.typroject.tyboot.core.foundation.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.typroject.tyboot.core.foundation.enumeration.UserType;

/**
 * 
 * <pre>
 *  Tyrest
 *  File: RequestContext.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:请求上下文,无论是http请求还是rpc请求,请求中所携带的上下文参数统一使用此类管理,请求上下文中的参数都是线程私有的
 *  TODO
 * 
 *  Notes:
 *  $Id: RequestContext.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月1日		magintrursh		Initial.
 *
 * </pre>
 */
public class RequestContext {

	private static final Logger logger = LoggerFactory.getLogger(RequestContext.class);

	private static ThreadLocal<RequestContextModel> requestContext = ThreadLocal.withInitial(RequestContextModel::new);

	private static RequestContextModel getRequestContext() {
		return requestContext.get();
	}

	public static RequestContextModel cloneRequestContext()
	{
		RequestContextModel contextModel = new RequestContextModel();
		BeanUtils.copyProperties(requestContext.get(),contextModel);
		return  contextModel;
	}


	public static String getLoginId() {
		return getRequestContext().getLoginId();
	}

	public static void setLoginId(String loginId) {
		getRequestContext().setLoginId(loginId);
	}

	public static void setToken(String token)
	{
		getRequestContext().setToken(token);
	}

	public static String getToken()
	{
		return getRequestContext().getToken();
	}

	public static void setRequestIP(String requsetIP) {
		getRequestContext().setRequestIP(requsetIP);
	}

	public static String getRequestIP() {
		return getRequestContext().getRequestIP();
	}

	public static String getUserAgent() {
		return getRequestContext().getUserAgent();
	}
	
	public static void setUserAgent(String userAgent) {
		getRequestContext().setUserAgent(userAgent);
	}

	public static void setAgencyCode(String agencyCode) {
		getRequestContext().setAgencyCode(agencyCode);
	}

	public static String getAgencyCode() {
		return getRequestContext().getAgencyCode();
	}

	public static void setUserType(UserType userType) {
		getRequestContext().setUserType(userType);
	}

	public static UserType getUserType() {
		return getRequestContext().getUserType();
	}

	public static void setExeUserId(String userId) {
		getRequestContext().setExcutedUserId(userId);
	}

	public static String getExeUserId() {
		return getRequestContext().getExcutedUserId();
	}

	public static String getAppVersion() {
		return getRequestContext().getAppVersion();
	}

	public static void setAppVersion(String version) {
		getRequestContext().setAppVersion(version);
	}

	public static String getTraceId() {
		return getRequestContext().getTraceId();
	}
	
	public static void setTraceId(String traceId) {
		getRequestContext().setTraceId(traceId);
	}

	public static String getBusinessTransactionId() {
		return getRequestContext().getBusinessTransactionId();
	}

	public static void setBusinessTransactionId(String businessTransactionId) {
		getRequestContext().setBusinessTransactionId(businessTransactionId);
	}

	public static String getProduct() {
		return getRequestContext().getProduct();
	}

	public static void setProduct(String product) {
		getRequestContext().setProduct(product);
	}

	public static void setAttribute(RequestContextEntityType key, Object value) {
		getRequestContext().setAttribute(key, value);
	}

	public static Object getAttribute(RequestContextEntityType key) {
		return getRequestContext().getAttribute(key);
	}

	public static  void setRequestTimeMills(Long requestTimeMills) {
		getRequestContext().setRequestTimeMills(requestTimeMills);
	}


	public static String getDeviceId() {
		return getRequestContext().getDeviceId();
	}

	public static void setDeviceId(String deviceId) {
		getRequestContext().setDeviceId(deviceId);
	}

	public static  Long getRequestTimeMills() {
		return getRequestContext().getRequestTimeMills();
	}

	public static  String getAppKey() {
		return getRequestContext().getAppKey();
	}

	public static void setAppKey(String appKey) {
		getRequestContext().setAppKey(appKey); 
	}


	public static void clean() {
		RequestContextModel requestModel = getRequestContext();
		if (requestModel != null) {
			logger.info("clean RestThreadLocal......Begin");
			requestModel.clean();
			requestModel = null;
			logger.info("clean RestThreadLocal......Done");
		}
	}
}

/*
 * $Log: av-env.bat,v $
 */