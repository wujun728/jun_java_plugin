package com.osmp.web.test;

import java.util.concurrent.CountDownLatch;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.UnhandledErrorListener;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2015年6月5日 下午2:47:45
 */
public class ZookeeperClient {

	public static final String CONNECT_STRING = "10.34.39.104:2181,10.34.39.103:2181,10.34.39.112:2181";

	public static final int MAX_RETRIES = 3;

	public static final int BASE_SLEEP_TIMEMS = 3000;

	public static final String NAME_SPACE = "/kaikai/gege";
	public static final CountDownLatch begin = new CountDownLatch(3);

	public CuratorFramework get() {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(BASE_SLEEP_TIMEMS, MAX_RETRIES);
		CuratorFramework client = CuratorFrameworkFactory.builder().connectString(CONNECT_STRING)
				.retryPolicy(retryPolicy).build();
		return client;
	}

	public boolean exists(CuratorFramework client, String path) throws Exception {
		Stat stat = client.checkExists().forPath(path);
		return !(stat == null);
	}

	public void create(CuratorFramework client, String path) throws Exception {
		client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
				.forPath(path, "hello zookeeper".getBytes());
		System.out.println("新建PATH /kaikai成功!");
	}

	public static void main(String[] args) throws Exception {

		ZookeeperClient zookeeper = new ZookeeperClient();
		CuratorFramework client = zookeeper.get();
		// 注册连接监听事件
		zookeeper.registerListeners(client);
		client.start();
		zookeeper.create(client, NAME_SPACE);
		begin.await();
	}

	// 注册需要监听的监听者对像.
	private void registerListeners(CuratorFramework client) {
		client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
			@SuppressWarnings("resource")
			@Override
			public void stateChanged(CuratorFramework client, ConnectionState newState) {
				System.out.println("CuratorFramework state changed: {}" + newState.name());
				if (newState == ConnectionState.CONNECTED || newState == ConnectionState.RECONNECTED) {
					System.out.println("与zookeeper连接状态 : connected");
					final NodeCache cache = new NodeCache(client, NAME_SPACE);
					cache.getListenable().addListener(new NodeCacheListener() {

						@Override
						public void nodeChanged() throws Exception {
							byte[] date = cache.getCurrentData().getData();
							System.out.println("节点数据已变" + new String(date));
							begin.countDown();
						}

					});
					try {
						cache.start(true);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else if (newState == ConnectionState.LOST) {
					System.out.println("与zookeeper连接状态 : lost");
				} else {
					System.out.println("与zookeeper连接状态 : 其它");
				}
			}
		});

		client.getUnhandledErrorListenable().addListener(new UnhandledErrorListener() {
			@Override
			public void unhandledError(String message, Throwable e) {
				System.out.println("CuratorFramework unhandledError: {}" + message);
				e.printStackTrace();
			}
		});
	}
}
