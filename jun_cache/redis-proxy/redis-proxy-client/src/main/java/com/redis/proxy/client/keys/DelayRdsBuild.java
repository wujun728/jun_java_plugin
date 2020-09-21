/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redis.proxy.client.keys;

import com.redis.proxy.client.register.KryoSeriClassRegistry;
import java.util.concurrent.TimeUnit;

/**
 * redis key 构建信息
 *
 * @author zhanggaofeng
 */
public class DelayRdsBuild extends RdsBuild {

        // 延迟：单位：秒
        protected final int delay;
        // 延迟：单位：秒
        protected final String time;

        public DelayRdsBuild(String module, String func, int version, int delay, TimeUnit unit, Class<?> clazz) {
                super(module, func, version, clazz);
                this.delay = (int) unit.toSeconds(delay);
                this.time = null;
                KryoSeriClassRegistry.registerClass(clazz);
        }

        public DelayRdsBuild(String module, String func, int version, String time, Class<?> clazz) {
                super(module, func, version, clazz);
                this.time = time;
                this.delay = 0;
                KryoSeriClassRegistry.registerClass(clazz);
        }

        @Override
        public RdsKey build(String... args) {
                if (time != null) {
                        return realBuild(getSeconds(time), args);
                } else {
                        return realBuild(delay, args);
                }
        }
}
