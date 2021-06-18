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
package net.oschina.j2cache.hibernate4.nonstop;

import net.oschina.j2cache.hibernate4.log.J2CacheMessageLogger;
import org.jboss.logging.Logger;

public final class HibernateNonstopCacheExceptionHandler {
    public static final String HIBERNATE_THROW_EXCEPTION_ON_TIMEOUT_PROPERTY = "J2Cache.hibernate.propagateNonStopCacheException";
    public static final String HIBERNATE_LOG_EXCEPTION_STACK_TRACE_PROPERTY = "J2Cache.hibernate.logNonStopCacheExceptionStackTrace";

    private static final J2CacheMessageLogger LOG = Logger.getMessageLogger(J2CacheMessageLogger.class, HibernateNonstopCacheExceptionHandler.class.getName());
    private static final HibernateNonstopCacheExceptionHandler INSTANCE = new HibernateNonstopCacheExceptionHandler();

    private HibernateNonstopCacheExceptionHandler() {
    }

    public static HibernateNonstopCacheExceptionHandler getInstance() {
        return INSTANCE;
    }

    public void handleNonstopCacheException(NonStopCacheException nonStopCacheException) {
        if (Boolean.getBoolean(HIBERNATE_THROW_EXCEPTION_ON_TIMEOUT_PROPERTY)) {
            throw nonStopCacheException;
        } else {
            if (Boolean.getBoolean(HIBERNATE_LOG_EXCEPTION_STACK_TRACE_PROPERTY)) {
                LOG.debug("Ignoring NonstopCacheException - " + nonStopCacheException.getMessage(), nonStopCacheException);
            } else {
                LOG.debug("Ignoring NonstopCacheException - " + nonStopCacheException.getMessage());
            }
        }
    }
}