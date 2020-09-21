/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redis.proxy.server.mgr;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.redis.proxy.common.utils.IpUtil;
import com.redis.proxy.common.zk.ZkSession;
import com.redis.proxy.common.consts.CacheConsts;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * zk config 类
 *
 * @author zhanggaofeng
 */
@Service
@Scope("singleton")
@DisconfFile(filename = "zk.properties")
public class ZkManager extends AbstDisconf {

        private ZkSession session = null;

        public ZkManager() {
                try {
                        this.fileLoad();
                } catch (Exception ex) {
                        ex.printStackTrace();
                }
                /**
                 * 销毁钩子
                 */
                Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                        @Override
                        public void run() {
                                if (session == null) {
                                        session.deleteProxyNode(IpUtil.getHostInfo(CacheConsts.SERVER_PORT));
                                        session.destory();
                                }
                        }
                }));
        }

        @Override
        protected void updateCallBack() {
                session = new ZkSession(getString("zk.hosts"), getInt("zk.connection.timeout", 10000), getInt("zk.session.timeout", 10000));
                session.registerProxyNode(IpUtil.getHostInfo(CacheConsts.SERVER_PORT));
        }

}
