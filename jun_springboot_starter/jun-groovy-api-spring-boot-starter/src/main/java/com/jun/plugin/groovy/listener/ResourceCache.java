package com.jun.plugin.groovy.listener;

import cn.hutool.core.collection.CollectionUtil;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 项目资源的缓存，存储了项目所有的访问url
 * <p>
 * 一般用在过滤器检测请求是否是项目没有的url
 *
 * @date 2020/7/9 11:03
 */
@Component
public class ResourceCache {

    private final Set<String> resourceCaches = CollectionUtil.newHashSet();

    /**
     * 获取所有缓存资源
     *
     * @date 2020/7/9 13:52
     */
    public Set<String> getAllResources() {
        return resourceCaches;
    }

    /**
     * 直接缓存所有资源
     *
     * @date 2020/7/9 13:52
     */
    public void putAllResources(Set<String> resources) {
        resourceCaches.addAll(resources);
    }

}
