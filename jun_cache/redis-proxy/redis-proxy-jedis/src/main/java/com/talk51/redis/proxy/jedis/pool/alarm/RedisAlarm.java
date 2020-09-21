/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.talk51.redis.proxy.jedis.pool.alarm;

import redis.clients.jedis.Jedis;

/**
 * redis服务连接报警接口
 *
 * @author zhanggaofeng
 */
public interface RedisAlarm {

        /**
         * 连接断开报警
         * @param jedis
         */
        public void disconnectAlarm(Jedis jedis);

        /**
         * 连接恢复开报警
         * @param jedis
         */
        public void reconnectAlarm(Jedis jedis);
}
