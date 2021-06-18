/**
 * Copyright (c) 2015-2017, liao.
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
package net.oschina.j2cache.hibernate3;

import net.oschina.j2cache.J2Cache;

import org.hibernate.cache.Cache;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.CacheProvider;
import org.hibernate.cache.Timestamper;

import java.util.Properties;

/**
 * 为 Hibernate 3 提供 J2Cache 的适配
 * @author Wujun
 */
@SuppressWarnings("deprecation")
public class J2CacheProvider implements CacheProvider {

    @Override
    public Cache buildCache(String regionName, Properties properties)
            throws CacheException {
        return new J2HibernateCache(regionName, J2Cache.getChannel());
    }

    @Override
    public boolean isMinimalPutsEnabledByDefault() {
        return true;
    }

    @Override
    public long nextTimestamp() {
        return Timestamper.next();
    }

    @Override
    public void start(Properties properties) throws CacheException {

    }

    @Override
    public void stop() {
        J2Cache.getChannel().close();
    }

}
