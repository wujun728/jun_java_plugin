/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redis.proxy.net.invoke;

import com.redis.proxy.net.command.RedisCmd;
import com.redis.proxy.net.resps.RdsResp;
import com.redis.proxy.net.handler.RdsMsgHandler;
import com.redis.proxy.net.resps.ErrorRdsResp;
import java.lang.reflect.Method;

/**
 * redis命令调用包裹实现
 *
 * @author zhanggaofeng
 */
public class RdsCmdInvokeImpl implements RdsCmdInvoke {

        private final Class<?>[] types;
        private final Method method;
        private final RdsMsgHandler handler;

        public RdsCmdInvokeImpl(Class<?>[] types, Method method, RdsMsgHandler handler) {
                this.types = types;
                this.method = method;
                this.handler = handler;
        }

        @Override
        public RdsResp<?> execute(String name, RedisCmd cmd) throws Exception {
                if (!cmd.checkArgNum(types.length)) {
                        return new ErrorRdsResp("wrong number of args for '" + name + "' command");
                }
                Object[] objects = new Object[types.length];
                cmd.toArguments(objects, types);
                return (RdsResp<?>) method.invoke(handler, objects);
        }

}
