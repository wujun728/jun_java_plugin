package io.github.wujun728.groovy.cache;

import io.github.wujun728.sql.entity.ApiConfig;

import java.util.Collection;
import java.util.List;


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
