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
package net.oschina.j2cache.hibernate4.log;

import org.hibernate.internal.CoreMessageLogger;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;

import static org.jboss.logging.Logger.Level.WARN;

@MessageLogger(projectCode = "HHH")
public interface J2CacheMessageLogger extends CoreMessageLogger {


    @LogMessage(level = WARN)
    @Message(
            value = "Attempt to restart an already started J2CacheRegionFactory.  Use sessionFactory.close() between " +
                    "repeated calls to buildSessionFactory. Using previously created J2CacheRegionFactory. If this " +
                    "behaviour is required, consider using org.hibernate.cache.j2cache.SingletonJ2CacheRegionFactory.",
            id = 20001
    )
    void attemptToRestartAlreadyStartedJ2CacheProvider();

    /**
     * Log a message (WARN) about inability to find configuration file
     *
     * @param name The name of the configuration file
     */
    @LogMessage(level = WARN)
    @Message(value = "Could not find configuration [%s]; using defaults.", id = 20002)
    void unableToFindConfiguration(String name);

    /**
     * Log a message (WARN) about inability to find named cache configuration
     *
     * @param name The name of the cache configuration
     */
    @LogMessage(level = WARN)
    @Message(value = "Could not find a specific J2Cache configuration for cache named [%s]; using defaults.", id = 20003)
    void unableToFindJ2CacheConfiguration(String name);

    /**
     * Logs a message about not being able to resolve the configuration by resource name.
     *
     * @param configurationResourceName The resource name we attempted to resolve
     */
    @LogMessage(level = WARN)
    @Message(
            value = "A configurationResourceName was set to %s but the resource could not be loaded from the classpath. " +
                    "J2Cache will configure itself using defaults.",
            id = 20004
    )
    void unableToLoadConfiguration(String configurationResourceName);

    /**
     * Logs a message (WARN) about attempt to use an incompatible
     */
    @LogMessage(level = WARN)
    @Message(
            value = "The default cache value mode for this J2Cache configuration is \"identity\". " +
                    "This is incompatible with clustered Hibernate caching - the value mode has therefore been " +
                    "switched to \"serialization\"",
            id = 20005
    )
    void incompatibleCacheValueMode();

    /**
     * Logs a message (WARN) about attempt to use an incompatible
     *
     * @param cacheName The name of the cache whose config attempted to specify value mode.
     */
    @LogMessage(level = WARN)
    @Message(value = "The value mode for the cache[%s] is \"identity\". This is incompatible with clustered Hibernate caching - "
            + "the value mode has therefore been switched to \"serialization\"", id = 20006)
    void incompatibleCacheValueModePerCache(String cacheName);

    /**
     * Log a message (WARN) about an attempt to specify read-only caching for a mutable entity
     *
     * @param entityName The name of the entity
     */
    @LogMessage(level = WARN)
    @Message(value = "read-only cache configured for mutable entity [%s]", id = 20007)
    void readOnlyCacheConfiguredForMutableEntity(String entityName);

    /**
     * Log a message (WARN) about expiry of soft-locked region.
     *
     * @param regionName The region name
     * @param key        The cache key
     * @param lock       The lock
     */
    @LogMessage(level = WARN)
    @Message(
            value = "Cache[%s] Key[%s] Lockable[%s]\n" +
                    "A soft-locked cache entry was expired by the underlying J2Cache. If this happens regularly you " +
                    "should consider increasing the cache timeouts and/or capacity limits",
            id = 20008
    )
    void softLockedCacheExpired(String regionName, Object key, String lock);

}