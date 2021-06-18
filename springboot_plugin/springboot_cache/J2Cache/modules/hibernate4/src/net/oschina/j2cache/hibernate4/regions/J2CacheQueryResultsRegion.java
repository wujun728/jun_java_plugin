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
package net.oschina.j2cache.hibernate4.regions;

import net.oschina.j2cache.hibernate4.CacheRegion;
import net.oschina.j2cache.hibernate4.strategy.J2CacheAccessStrategyFactory;
import org.hibernate.cache.spi.QueryResultsRegion;

import java.util.Properties;

public class J2CacheQueryResultsRegion extends J2CacheGeneralDataRegion implements QueryResultsRegion {

    public J2CacheQueryResultsRegion(J2CacheAccessStrategyFactory accessStrategyFactory, CacheRegion underlyingCache, Properties properties) {
        super( accessStrategyFactory, underlyingCache, properties );
    }

}