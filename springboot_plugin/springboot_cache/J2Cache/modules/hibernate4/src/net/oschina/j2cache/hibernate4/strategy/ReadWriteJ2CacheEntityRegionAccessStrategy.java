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
package net.oschina.j2cache.hibernate4.strategy;

import net.oschina.j2cache.hibernate4.regions.J2CacheEntityRegion;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.EntityRegion;
import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.cfg.Settings;


public class ReadWriteJ2CacheEntityRegionAccessStrategy extends AbstractReadWriteJ2CacheAccessStrategy<J2CacheEntityRegion> implements EntityRegionAccessStrategy {

    public ReadWriteJ2CacheEntityRegionAccessStrategy(J2CacheEntityRegion region, Settings settings) {
        super( region, settings );
    }

    @Override
    public EntityRegion getRegion() {
        return region();
    }

    @Override
    public boolean insert(Object key, Object value, Object version) throws CacheException {
        return false;
    }

    @Override
    public boolean afterInsert(Object key, Object value, Object version) throws CacheException {
        region().writeLock( key );
        try {
            final AbstractReadWriteJ2CacheAccessStrategy.Lockable item = (AbstractReadWriteJ2CacheAccessStrategy.Lockable) region().get( key );
            if ( item == null ) {
                region().put( key, new AbstractReadWriteJ2CacheAccessStrategy.Item( value, version, region().nextTimestamp() ) );
                return true;
            }
            else {
                return false;
            }
        }
        finally {
            region().writeUnlock( key );
        }
    }

    @Override
    public boolean update(Object key, Object value, Object currentVersion, Object previousVersion)
            throws CacheException {
        return false;
    }

    @Override
    public boolean afterUpdate(Object key, Object value, Object currentVersion, Object previousVersion, SoftLock lock)
            throws CacheException {
        //what should we do with previousVersion here?
        region().writeLock( key );
        try {
            final AbstractReadWriteJ2CacheAccessStrategy.Lockable item = (AbstractReadWriteJ2CacheAccessStrategy.Lockable) region().get( key );

            if ( item != null && item.isUnlockable( lock ) ) {
                final AbstractReadWriteJ2CacheAccessStrategy.Lock lockItem = (AbstractReadWriteJ2CacheAccessStrategy.Lock) item;
                if ( lockItem.wasLockedConcurrently() ) {
                    decrementLock( key, lockItem );
                    return false;
                }
                else {
                    region().put( key, new AbstractReadWriteJ2CacheAccessStrategy.Item( value, currentVersion, region().nextTimestamp() ) );
                    return true;
                }
            }
            else {
                handleLockExpiry( key, item );
                return false;
            }
        }
        finally {
            region().writeUnlock( key );
        }
    }

}
