/**
 * Copyright (c) 2015-2017, Winter Lau (javayou@gmail.com).
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

import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.J2Cache;

import org.hibernate.cache.*;
import org.hibernate.cfg.Settings;

import java.util.Properties;

/**
 * J2Cache Hibernate RegionFactory implementations.
 *
 * @author Wujun
 */
public class J2CacheRegionFactory implements RegionFactory {

    private CacheChannel channel = J2Cache.getChannel();

    @Override
    public CollectionRegion buildCollectionRegion(String regionName, Properties properties, CacheDataDescription metadata) throws CacheException {
        return new J2CacheRegion.Collection(regionName, channel);
    }

    @Override
    public EntityRegion buildEntityRegion(String regionName, Properties properties, CacheDataDescription metadata) throws CacheException {
        return new J2CacheRegion.Entity(regionName, channel);
    }

    @Override
    public QueryResultsRegion buildQueryResultsRegion(String regionName, Properties properties) throws CacheException {
        return new J2CacheRegion.QueryResults(regionName, channel);
    }

    @Override
    public TimestampsRegion buildTimestampsRegion(String regionName, Properties properties) throws CacheException {
        return new J2CacheRegion.Timestamps(regionName, channel);
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
    public void start(Settings settings, Properties properties) throws CacheException {

    }

    @Override
    public void stop() {
        channel.close();
    }

}
