/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redis.proxy.common.zk;

import com.redis.proxy.common.consts.CacheConsts;
import java.util.List;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkException;
import org.I0Itec.zkclient.exception.ZkInterruptedException;

/**
 * zk会话
 *
 * @author zhanggaofeng
 */
public class ZkSession {

        private final static String ROOT = "/PROXYS";
        private final static String PROXYS = ROOT + "/" + CacheConsts.SERVER_GROUP;

        private ZkClient zkClient = null;

        public ZkSession(String zkHosts, int connectionTimeout, int sessionTimeout) {
                if (zkClient == null) {
                        zkClient = new ZkClient(zkHosts, sessionTimeout, connectionTimeout);
                }
                if (!zkClient.exists(PROXYS)) {
                        zkClient.createPersistent(PROXYS, true);
                }
        }

        public void destory() {
                if (zkClient != null) {
                        zkClient.close();
                        zkClient = null;
                }
        }

        /**
         * 获取代理父节点
         *
         * @return
         */
        public String getProxysPath() {
                return PROXYS;
        }

        /**
         * 删除代理节点
         *
         * @param path
         * @return
         */
        public boolean deleteProxyNode(String path) {
                return zkClient.deleteRecursive(PROXYS + "/" + path);
        }

        /**
         * 注册代理节点
         *
         * @param path
         * @throws ZkInterruptedException
         * @throws IllegalArgumentException
         * @throws ZkException
         * @throws RuntimeException
         */
        public void registerProxyNode(String path) throws ZkInterruptedException, IllegalArgumentException, ZkException, RuntimeException {
                zkClient.createEphemeral(PROXYS + "/" + path);
        }

        /**
         * 订阅代理节点变化
         *
         * @param listener
         * @return
         */
        public List<String> subscribeProxyChildChanges(IZkChildListener listener) {
                return zkClient.subscribeChildChanges(PROXYS, listener);
        }

        /**
         * 取消订阅代理节点变化
         *
         * @param listener
         */
        public void unsubscribeProxyChildChanges(IZkChildListener listener) {
                zkClient.unsubscribeChildChanges(PROXYS, listener);
        }
}
