package com.github.ghsea.rpc.common.modle;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;

public abstract class BaseProfile implements Serializable {

	private static final long serialVersionUID = 6177152089349715374L;

	private String pool;

	private String service;

	private String version;

	protected BaseProfile() {

	}

	protected BaseProfile(String pool, String service, String version) {
		Preconditions.checkArgument(StringUtils.isNotBlank(pool));
		Preconditions.checkArgument(StringUtils.isNotBlank(service));
		Preconditions.checkArgument(StringUtils.isNotBlank(version));
		this.pool = pool;
		this.service = service;
		this.version = version;
	}

	public String getPool() {
		return pool;
	}

	public String getVersion() {
		return version;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

}
