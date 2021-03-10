package org.typroject.tyboot.component.opendata.storage.model;

import java.io.Serializable;

/** 
 * 
 * <pre>
 *  Tyrest
 *  File: QiniuResourceModel.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 *  $Id: QiniuResourceModel.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月17日		magintrursh		Initial.
 *
 * </pre>
 */
public class QiniuResourceModel implements Serializable {

	private static final long serialVersionUID = -1359042377131749888L;

	private String key;

	private String realFileName;

	private Long putTime;

	private String hash;

	private Long fsize;

	private String mimeType;

	private String customer;

	private String fileName;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Long getPutTime() {
		return putTime;
	}

	public void setPutTime(Long putTime) {
		this.putTime = putTime;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Long getFsize() {
		return fsize;
	}

	public void setFsize(Long fsize) {
		this.fsize = fsize;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getRealFileName() {
		return realFileName;
	}

	public void setRealFileName(String realFileName) {
		this.realFileName = realFileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}

/*
 * $Log: av-env.bat,v $
 */