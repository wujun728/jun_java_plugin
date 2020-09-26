/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redis.proxy.client.register;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 本项目使用kryo序列化对象，为了提高对象序列化效率，最好把注册的对象注册下 ---------
 * 为了提高kryo序列化的性能-可以注册被序列化的对象class ---------
 *
 * @author zhanggaofeng
 */
public abstract class KryoSeriClassRegistry {

        private static final Set<Class<?>> registrations = new LinkedHashSet<>();

        public static void registerClass(Class<?> clazz) {

                boolean successRegister = false;
                Class[] interfaceClazzes = clazz.getInterfaces();
                for (Class interfaceClazz : interfaceClazzes) {
                        if (interfaceClazz instanceof Serializable) {
                                registrations.add(clazz);
                                successRegister = true;
                        }
                }
                if (!successRegister) {
                        throw new IllegalArgumentException("kryo序列化对象必须继承 Serializable 类");
                }
        }

        public static Set<Class<?>> getRegisteredClasses() {
                return registrations;
        }

}
