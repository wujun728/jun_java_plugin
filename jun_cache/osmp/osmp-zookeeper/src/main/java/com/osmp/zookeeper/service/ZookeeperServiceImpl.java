/*   
 * Project: OSMP
 * FileName: ZookeeperServiceImpl.java
 * version: V1.0
 */
package com.osmp.zookeeper.service;

import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.osmp.intf.define.service.ZookeeperService;
import com.osmp.utils.net.RequestInfoHelper;

/**
 * Description: ZOOKEEPER实现类
 * 
 * @author: wangkaiping
 * @date: 2015年6月9日 上午11:27:18
 */
public class ZookeeperServiceImpl implements ZookeeperService {

	private static final Logger logger = LoggerFactory
			.getLogger(ZookeeperServiceImpl.class);
	private static CuratorFramework client;
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 创建永久节点
	 * 
	 * @param path
	 * @param data
	 */
	@Override
	public void createNode(String path) throws Exception {
		client.create().creatingParentsIfNeeded()
				.withMode(CreateMode.PERSISTENT).forPath(path);
	}

	/**
	 * 创建永久节点带数据
	 * 
	 * @param path
	 * @param data
	 * @throws Exception
	 */
	@Override
	public void createNode(String path, String data) throws Exception {
		client.create().creatingParentsIfNeeded()
				.withMode(CreateMode.PERSISTENT).forPath(path, data.getBytes());

	}

	/**
	 * 创建临时节点
	 * 
	 * @param path
	 * @param data
	 */
	@Override
	public void createEpheMeralNode(String path) throws Exception {
		client.create().creatingParentsIfNeeded()
				.withMode(CreateMode.EPHEMERAL).forPath(path);

	}

	/**
	 * 修改节点数据
	 * 
	 * @param path
	 * @param data
	 */
	@Override
	public void setNodeData(String path, String data) throws Exception {
		client.setData().forPath(path, data.getBytes());

	}

	/**
	 * 删除节点
	 * 
	 * @param path
	 */
	@Override
	public void deleteNode(String path) throws Exception {
		client.delete().deletingChildrenIfNeeded().forPath(path);

	}

	/**
	 * 节点是否存在
	 * 
	 * @param path
	 * @return
	 */
	@Override
	public boolean exists(String path) throws Exception {
		Stat stat = client.checkExists().forPath(path);
		return !(stat == null);
	}

	/**
	 * 得到节点下所有子节点信息
	 * 
	 * @param path
	 * @return
	 */
	@Override
	public List<String> getChilds(String path) throws Exception {
		return client.getChildren().forPath(path);
	}

	@Override
	public boolean isConnected() {
		return client.getZookeeperClient().isConnected();
	}

	public void close() {
		client.close();
	}

	/**
	 * 初始化zookeeper client 连接成功后注册本机状态到zk (注册为临时节点，节点名称为本机IP地址)
	 */
	public void init() {
		ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 1);
		client = CuratorFrameworkFactory.newClient(url, retryPolicy);
		client.start();
		client.getConnectionStateListenable().addListener(
				new ConnectionStateListener() {
					@Override
					public void stateChanged(CuratorFramework client,
							ConnectionState newState) {
						if (ConnectionState.CONNECTED == newState
								|| ConnectionState.RECONNECTED == newState) {
							ZookeeperServiceImpl.this.regStatus();
							logger.info("zookeeper client connect status is connected ");
						} else if (ConnectionState.LOST == newState) {
							logger.info("zookeeper client connect status is lost ");
						} else if (ConnectionState.SUSPENDED == newState) {
							logger.info("zookeeper client connect status is suspended ");
						}
					}
				});
	}

	/**
	 * 注册状态
	 */
	public void regStatus() {
		String path = ZookeeperService.ROOT_PATH
				+ ZookeeperService.STATUS + "/";
		String ip = RequestInfoHelper.getLocalIp();
		path += ip;
		try {
			if (!this.exists(path)) {
				this.createEpheMeralNode(path);
				logger.info("register system status is success, path = " + path);
			}
		} catch (Exception e) {
			logger.error("register system status is fail, path = " + path, e);
		}
	}

	public static void main(String[] args) throws Exception {
		ZookeeperServiceImpl zk = new ZookeeperServiceImpl();
		zk.setUrl("10.34.39.112:2181,10.34.39.113:2181,10.34.39.114:2181");
		zk.init();
		zk.deleteNodeByBundle("osmp-ltwo");
		Thread.sleep(2 * 60 * 1000);
	}

	/**
	 * 根据bundle删除服务节点
	 * 
	 * @param bundle
	 * @throws Exception
	 */
	@Override
	public void deleteNodeByBundle(String bundle) throws Exception {
		String path = ZookeeperService.ROOT_PATH + ZookeeperService.SERVICE + "/";
		String ip = RequestInfoHelper.getLocalIp();
		path += ip;
		List<String> list = this.getChilds(path);
		if (null != list && list.size() > 0) {
			for (String i : list) {
				String data = this.getNodeDate(path + "/" + i + "/bundle");
				if (bundle.equals(data)) {
					this.deleteNode(path + "/" + i);
					logger.info("uninstall bundle: "
							+ "bundle and delete service : " + path + "/" + i
							+ " from zookeeper...");
				}
			}
		}

	}

	/**
	 * 获取节点的数据
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getNodeDate(String path) throws Exception {
		byte[] content = client.getData().forPath(path);
		String str = new String(content);
		return str;
	}

}
