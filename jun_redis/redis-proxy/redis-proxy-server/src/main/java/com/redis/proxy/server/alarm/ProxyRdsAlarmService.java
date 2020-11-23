/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redis.proxy.server.alarm;

import com.talk51.redis.proxy.jedis.pool.alarm.RedisAlarm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * 代理服务集群的redis报警接口
 *
 * @author Wujun
 */
@Service
public class ProxyRdsAlarmService implements RedisAlarm {

        private Logger logger = LoggerFactory.getLogger(ProxyRdsAlarmService.class);

        @Override
        public void disconnectAlarm(Jedis jedis) {
                logger.warn("disconnect host = " + jedis.getClient().getHost() + ":" + jedis.getClient().getPort());
        }

        @Override
        public void reconnectAlarm(Jedis jedis) {
                logger.warn("reconnect host = " + jedis.getClient().getHost() + ":" + jedis.getClient().getPort());
        }

}
