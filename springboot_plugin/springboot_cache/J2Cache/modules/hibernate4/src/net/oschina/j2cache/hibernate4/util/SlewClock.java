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
package net.oschina.j2cache.hibernate4.util;

import java.util.concurrent.atomic.AtomicLong;


public class SlewClock {

    private static final TimeProvider PROVIDER = TimeProviderLoader.getTimeProvider();

    private static final long DRIFT_MAXIMAL = Integer.getInteger("net.oschina.j2cache.hibernate4.redis.util.Timestamper.drift.max", 50);

    private static final long SLEEP_MAXIMAL = Integer.getInteger("net.oschina.j2cache.hibernate4.redis.util.Timestamper.sleep.max", 50);

    private static final int  SLEEP_BASE    = Integer.getInteger("net.oschina.j2cache.hibernate4.redis.util.Timestamper.sleep.min", 25);

    private static final AtomicLong CURRENT = new AtomicLong(getCurrentTime());

    private static final VicariousThreadLocal<Long> OFFSET = new VicariousThreadLocal<Long>();

    private SlewClock() {
    }

    static long timeMillis() {
        boolean interrupted = false;
        try {
            while (true) {
                long mono = CURRENT.get();
                long wall = getCurrentTime();
                if (wall == mono) {
                    OFFSET.remove();
                    return wall;
                } else if (wall >= mono) {
                    if (CURRENT.compareAndSet(mono, wall)) {
                        OFFSET.remove();
                        return wall;
                    }
                } else {
                    long delta = mono - wall;
                    if (delta < DRIFT_MAXIMAL) {
                        OFFSET.remove();
                        return mono;
                    } else {
                        Long lastDelta = OFFSET.get();
                        if (lastDelta == null || delta < lastDelta) {
                            long update = wall - delta;
                            update = update < mono ? mono + 1 : update;
                            if (CURRENT.compareAndSet(mono, update)) {
                                OFFSET.set(delta);
                                return update;
                            }
                        } else {
                            try {
                                Thread.sleep(sleepTime(delta, lastDelta));
                            } catch (InterruptedException e) {
                                interrupted = true;
                            }
                        }
                    }
                }
            }
        } finally {
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static boolean isThreadCatchingUp() {
        return OFFSET.get() != null;
    }

    static long behind() {
        Long offset = OFFSET.get();
        return offset == null ? 0 : offset;
    }

    private static long sleepTime(final long current, final long previous) {
        long target = SLEEP_BASE + (current - previous) * 2;
        return Math.min(target > 0 ? target : SLEEP_BASE, SLEEP_MAXIMAL);
    }

    private static long getCurrentTime() {
        return PROVIDER.currentTimeMillis();
    }

    interface TimeProvider {

        long currentTimeMillis();

    }
}
