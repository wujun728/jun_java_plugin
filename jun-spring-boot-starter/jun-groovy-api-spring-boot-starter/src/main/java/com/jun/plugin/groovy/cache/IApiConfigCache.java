package com.jun.plugin.groovy.cache;

import java.util.Collection;
import java.util.List;

import com.jun.plugin.groovy.common.model.ApiConfig;

/**
 * API信息缓存
 */
public interface IApiConfigCache {
	public ApiConfig get(ApiConfig apiInfo);

	public ApiConfig get(String path);

	public void put(ApiConfig apiInfo);

	public void remove(ApiConfig apiInfo);

	public void removeAll();

	public Collection<ApiConfig> getAll();

	public void  putAll(List<ApiConfig> apiInfos);
}
