/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redis.proxy.net.invoke;

import com.redis.proxy.net.command.RedisCmd;
import com.redis.proxy.net.resps.RdsResp;

/**
 * redis命令调用包裹实现
 *
 * @author Wujun
 */
public interface RdsCmdInvoke {

        RdsResp<?> execute(String name, RedisCmd command) throws Exception;
}
