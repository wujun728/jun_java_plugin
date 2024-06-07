package com.jun.plugin.groovy.cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.jun.plugin.groovy.common.model.ApiConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

//import cn.hutool.core.util.IdUtil;
//import cn.hutool.core.util.StrUtil;

/**
 * API信息缓存
 */
@Slf4j
@Component
public class ApiConfigCache implements IApiConfigCache {

    private Map<String, ApiConfig> cacheApiConfig = new ConcurrentHashMap<>();

    private String instanceId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 15);;
//    private String instanceId = IdUtil.generateUUID();

    @Override
    public ApiConfig get(ApiConfig apiInfo){
        return cacheApiConfig.get(buildApiConfigKey(apiInfo));
    }
    @Override
    public ApiConfig get(String path){
    	return cacheApiConfig.get(path);
    }

    @Override
    public Collection<ApiConfig> getAll() {
        return cacheApiConfig.values();
    }

    @Override
    public void removeAll() {
        cacheApiConfig.clear();
    }

    @Override
    public void remove(ApiConfig apiInfo) {
        cacheApiConfig.remove(buildApiConfigKey(apiInfo));
    }

    @Override
    public void put(ApiConfig apiInfo) {
        cacheApiConfig.put(buildApiConfigKey(apiInfo),apiInfo);
    }
    @Override
    public void putAll(List<ApiConfig> apiInfos) {
    	this.removeAll();
    	for(ApiConfig apiInfo : apiInfos) {
    		this.put(apiInfo);
    	}
    }

    private String buildApiConfigKey(ApiConfig apiInfo) {
//    	if(StrUtil.isNotEmpty(apiInfo.getMethod())) {
//    		return apiInfo.getMethod() +" "+ apiInfo.getPath();
//    	}
    	return apiInfo.getPath();
    }

    	public static String getByPath(String path) {
		return beanNameMap.get(path);
	}








    /**
     //	 * 脚本列表
     	 */
	private static ConcurrentMap<String, ApiConfig> groovyMap = new ConcurrentHashMap<>();

	/**
	 * 缓存 beanNameList脚本列表
	 */
	private static ConcurrentMap<String, String> beanNameMap = new ConcurrentHashMap<>();

	/**
	 * 把脚本缓存一下
	 *
	 * @param groovyList
	 */
	public static void put2map(List<ApiConfig> groovyList) {

		// 先清空
		if (!beanNameMap.isEmpty()) {
			beanNameMap.clear();
		}
		if (!groovyMap.isEmpty()) {
			groovyMap.clear();
		}
		for (ApiConfig groovyInfo : groovyList) {
			String scriptName = groovyInfo.getBeanName();
			if (!groovyMap.containsKey(scriptName)) {
				groovyMap.put(scriptName, groovyInfo);
			} else {
				// 发现重名groovy脚本
				log.warn("found duplication groovy script:" + groovyInfo);
			}
			// 缓存 beanNameList
			if (!beanNameMap.containsKey(groovyInfo.getPath())) {
				beanNameMap.put(groovyInfo.getPath(), groovyInfo.getBeanName());
			} else {
				log.warn("found duplication path:" + groovyInfo.getPath());
			}
		}
	}


	/**
	 * 更新map
	 *
	 * @param groovyInfos
	 */
	public static void update2map(List<ApiConfig>[] groovyInfos) {
		List<ApiConfig> addedApiConfigs = groovyInfos[0];
		List<ApiConfig> updatedApiConfigs = groovyInfos[1];
		List<ApiConfig> deletedApiConfigs = groovyInfos[2];
		addMap(addedApiConfigs);
		addMap(updatedApiConfigs);
		removeMap(deletedApiConfigs);
	}

	/**
	 * 新增
	 * @param groovyList
	 */
	private static void addMap(List<ApiConfig> groovyList) {
		for (ApiConfig groovyInfo : groovyList) {
			groovyMap.put(groovyInfo.getBeanName(), groovyInfo);
		}
	}

	/**
	 * 删除
	 * @param groovyList
	 */
	private static void removeMap(List<ApiConfig> groovyList) {
		for (ApiConfig groovyInfo : groovyList) {
			groovyMap.remove(groovyInfo.getBeanName());
		}
	}

	/**
	 * 根据名称获取脚本信息
	 * @param scriptName
	 * @return
	 */
	public static ApiConfig getByName(String scriptName) {
		return groovyMap.get(scriptName);
	}


	public static ApiConfig getApiConfigByPath(String Path) {
		String beanName = beanNameMap.get(Path);
		if(ObjectUtils.isEmpty(beanName)) {
			log.error("beanname-{} 不能为空 ",beanNameMap.get(Path));
			log.error("Path-{} 没有注册的Bean ",Path);
		}
		ApiConfig info = groovyMap.get(beanNameMap.get(Path));
		if(ObjectUtils.isEmpty(info)) {
			log.error("Path-{} 不能为空 ",Path);
		}
		return info;
	}

	public static Map<String, ApiConfig> getApiConfigs() {
		return groovyMap;
	}

	public static Map<String, String> getBeanNameMap() {
		return beanNameMap;
	}

}
