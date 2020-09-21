package com.redis.proxy.client.keys;

import java.nio.charset.Charset;

/**
 *
 * @author zhanggaofeng
 */
public class RdsKey {

        // Redis key
        protected final byte[] key;
        protected final String keyStr;
        // 延迟：单位：秒
        protected final int delay;
        // 所属类
        protected final Class<?> clazz;

        public RdsKey(String key, int delay, Class<?> clazz) {
                this.keyStr = key;
                this.key = keyStr.getBytes(Charset.forName("utf8"));
                this.delay = delay;
                this.clazz = clazz;
        }

        public byte[] getKey() {
                return key;
        }

        public String getKeyStr() {
                return keyStr;
        }

        public int getDelay() {
                return delay;
        }

        public Class<?> getClazz() {
                return clazz;
        }

}
