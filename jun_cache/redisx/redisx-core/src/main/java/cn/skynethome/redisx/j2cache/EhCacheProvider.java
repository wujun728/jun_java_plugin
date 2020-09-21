/**
 *  Copyright 2014-2015 Oschina
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cn.skynethome.redisx.j2cache;

import java.io.File;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ehcache.CacheManager;

/**
  * 项目名称:[redisx-core]
  * 包:[cn.skynethome.redisx.j2cache]    
  * 文件名称:[EhCacheProvider]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月11日 上午9:41:25]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月11日 上午9:41:25]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class EhCacheProvider implements CacheProvider
{

    private final static Logger log = LoggerFactory.getLogger(EhCacheProvider.class);
    public static String KEY_EHCACHE_NAME = "ehcache.name";
    public static String KEY_EHCACHE_CONFIG_XML = "ehcache.configXml";

    private static CacheManager manager;
    private static ConcurrentHashMap<String, EhCache> _CacheManager;

    @Override
    public String name()
    {
        return "ehcache";
    }

    /**
     * Builds a Cache.
     * Even though this method provides properties, they are not used.
     * Properties for EHCache are specified in the ehcache.xml file.
     * Configuration will be read from ehcache.xml for a cache declaration
     * where the name attribute matches the name parameter in this builder.
     *
     * @param name the name of the cache. Must match a cache configured in ehcache.xml
     * @param autoCreate auto create cache region ?
     * @param listener cache listener
     * @return a newly built cache will be built and initialised
     * @throws CacheException inter alia, if a cache of the same name already exists
     */
    public EhCache buildCache(String name, boolean autoCreate, CacheExpiredListener listener) throws CacheException
    {
        EhCache ehcache = _CacheManager.get(name);
        if (ehcache == null && autoCreate)
        {
            try
            {
                synchronized (_CacheManager)
                {
                    ehcache = _CacheManager.get(name);
                    if (ehcache == null)
                    {
                        net.sf.ehcache.Cache cache = manager.getCache(name);
                        if (cache == null)
                        {
                            log.warn("Could not find configuration [" + name + "]; using defaults.");
                            manager.addCache(name);
                            cache = manager.getCache(name);
                            log.debug("started EHCache region: " + name);
                        }
                        ehcache = new EhCache(cache, listener);
                        _CacheManager.put(name, ehcache);
                    }
                }
            }
            catch (net.sf.ehcache.CacheException e)
            {
                throw new CacheException(e);
            }
        }
        return ehcache;
    }

    /**
     * Callback to perform any necessary initialization of the underlying cache implementation
     * during SessionFactory construction.
     *
     * @param props current configuration settings.
     */
    public void start(Properties props) throws CacheException
    {

        if (manager != null)
        {
            log.warn("Attempt to restart an already started EhCacheProvider.");
            return;
        }

        // 如果指定了名称,那么尝试获取已有实例
        String ehcacheName = (String) props.get(KEY_EHCACHE_NAME);
        if (ehcacheName != null && ehcacheName.trim().length() > 0)
            manager = CacheManager.getCacheManager(ehcacheName);
        if (manager == null)
        {
            // 指定了配置文件路径? 加载之
            if (props.containsKey(KEY_EHCACHE_CONFIG_XML))
            {

                String server_home = System.getProperty("server.home");

                if (!StringUtils.isEmpty(server_home))
                {

                    String urlStr = "";

                    urlStr = server_home + File.separator + "conf" + File.separator
                            + props.getProperty(KEY_EHCACHE_CONFIG_XML);
                    manager = new CacheManager(urlStr);
                }
                else
                {

                    URL url = this.getClass().getResource("/"+props.getProperty(KEY_EHCACHE_CONFIG_XML));
                    manager = new CacheManager(url);

                }

            }
            else
            {
                // 加载默认实例
                manager = CacheManager.getInstance();
            }
        }
        _CacheManager = new ConcurrentHashMap<String, EhCache>();
    }

    /**
     * Callback to perform any necessary cleanup of the underlying cache implementation.
     */
    public void stop()
    {
        if (manager != null)
        {
            manager.shutdown();
            _CacheManager.clear();
            manager = null;
        }
    }

}
