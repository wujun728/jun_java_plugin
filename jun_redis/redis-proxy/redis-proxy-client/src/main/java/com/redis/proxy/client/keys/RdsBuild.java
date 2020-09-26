/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redis.proxy.client.keys;

import java.util.Calendar;

/**
 *
 * @author zhanggaofeng
 */
public abstract class RdsBuild {

        /**
         * 一天的毫秒数
         */
        protected static final long dayTm = 24 * 60 * 60 * 1000L;
        // Redis key
        protected final String key;
        // 所属类
        protected final Class<?> clazz;

        protected RdsBuild(String module, String func, int version, Class<?> clazz) {
                this.key = module + "_" + func + "_" + version;
                this.clazz = clazz;
        }

        protected int getSeconds(String time) {

                String[] arr = time.split("\\:");
                Calendar cal = Calendar.getInstance();
                long now = cal.getTimeInMillis();
                cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arr[0]));
                cal.set(Calendar.MINUTE, Integer.parseInt(arr[1]));
                cal.set(Calendar.SECOND, Integer.parseInt(arr[2]));
                cal.set(Calendar.MILLISECOND, 0);
                long delayTm = cal.getTimeInMillis();
                if (delayTm <= now - 1000) {
                        cal.setTimeInMillis(delayTm + dayTm);
                        return (int) cal.getTimeInMillis();
                }
                return (int) (delayTm / 1000);
        }

        protected RdsKey realBuild(int delay, String... args) {
                String buildKey = key;
                if (args != null && args.length > 0) {
                        for (int i = 0; i < args.length; i++) {
                                buildKey += ("_" + args[i]);
                        }
                }
                return new RdsKey(buildKey, delay, clazz);
        }

        public abstract RdsKey build(String... args);
}
