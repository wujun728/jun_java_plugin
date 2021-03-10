package com.gosalelab.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EHCacheProperties {
    private static final Logger logger = LoggerFactory.getLogger(EHCacheProperties.class);

    private boolean useXmlFileConfig = false;

    private String ehcacheFileName = "ehcache.xml";

    private String defaultCacheName = "ehcache_cache";

    private int maxEntriesLocalHeap = 1000;

    private int offHeap = 20;

    private int disk = 200;

    public String getEhcacheFileName() {
        return ehcacheFileName;
    }

    public void setEhcacheFileName(String ehcacheFileName) {
        this.ehcacheFileName = ehcacheFileName;
    }

    public String getDefaultCacheName() {
        return defaultCacheName;
    }

    public void setDefaultCacheName(String defaultCacheName) {
        this.defaultCacheName = defaultCacheName;
    }

    public int getMaxEntriesLocalHeap() {
        return maxEntriesLocalHeap;
    }

    public void setMaxEntriesLocalHeap(int maxEntriesLocalHeap) {
        this.maxEntriesLocalHeap = maxEntriesLocalHeap;
    }

    public int getOffHeap() {
        return offHeap;
    }

    public void setOffHeap(int offHeap) {
        this.offHeap = offHeap;
    }

    public int getDisk() {
        return disk;
    }

    public void setDisk(int disk) {
        this.disk = disk;
    }

    public boolean isUseXmlFileConfig() {
        return useXmlFileConfig;
    }

    public void setUseXmlFileConfig(boolean useXmlFileConfig) {
        this.useXmlFileConfig = useXmlFileConfig;
    }
}
