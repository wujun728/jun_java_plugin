package com.jun.plugin.dfs.base.cache;

import com.google.common.collect.Maps;
import com.jun.plugin.dfs.core.entity.AppInfoEntity;

import java.util.Map;

public class CacheService {

    public final static Map<String, AppInfoEntity> APP_INFO_CACHE = Maps.newConcurrentMap();
}
