/**
 * Copyright (c) 2015-2017.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.oschina.j2cache.hibernate4;

import net.oschina.j2cache.J2Cache;

import org.hibernate.cache.CacheException;
import org.hibernate.cfg.Settings;

import java.util.Properties;


public class J2CacheRegionFactory extends AbstractJ2CacheRegionFactory {

    private static final String SPRING_CACHEMANAGER = "hibernate.cache.spring.cache_manager";

    private static final String DEFAULT_SPRING_CACHEMANAGER = "cacheManager";

    @SuppressWarnings("UnusedDeclaration")
    public J2CacheRegionFactory() {
    }

    @SuppressWarnings("UnusedDeclaration")
    public J2CacheRegionFactory(Properties prop) {
        super();
    }

    @Override
    public void start(Settings settings, Properties properties) throws CacheException {
        this.settings = settings;
        if (this.channel == null) {
            this.channel = J2Cache.getChannel();
        }
    }

    @Override
    public void stop() {
        if (channel != null) {
            channel.close();
        }
    }

}