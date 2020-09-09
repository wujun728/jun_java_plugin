package com.github.ghsea.rpc.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ghsea.rpc.common.constants.PropertiesConstants;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class ZkClient {

	private Logger log = LoggerFactory.getLogger(ZkClient.class);

	private int retryTimes = 3;

	private CuratorFramework client;

	private ExecutorService childCacheBackgroundExecutor;

	private WeakHashMap<String, PathChildrenCache> path2ChildrenCache = new WeakHashMap<String, PathChildrenCache>();

	private static volatile ZkClient zkClient;

	private static Object lock = new Object();

	public ZkClient() {
		init();
	}

	public static ZkClient getInstance() {
		if (zkClient == null) {
			synchronized (lock) {
				if (zkClient == null) {
					zkClient = new ZkClient();
				}
			}
		}
		return zkClient;
	}

	private void init() {
		long start = System.currentTimeMillis();
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
		PropertiesConfiguration config = PropertiesConfigHelper.loadIfAbsent(PropertiesConstants.ZK_CONFIG_FILE);
		String zkServers = StringUtils.join(config.getList(PropertiesConstants.KEY_ZK_SERVERS), ",");
		Preconditions.checkNotNull(zkServers);
		client = CuratorFrameworkFactory.newClient(zkServers, retryPolicy);
		client.start();
		boolean isConnected = false;
		int retried = 0;
		log.debug("Connecting to ZkServer...");
		while (!isConnected && retried <= retryTimes) {
			try {
				isConnected = client.blockUntilConnected(10, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				log.error(e.getMessage(), e);
			}
			retried = retried + 1;
		}

		if (!isConnected) {
			throw new RuntimeException("failed to connect to Zkservers " + zkServers + " after tried " + retried
					+ " times.");
		}

		ThreadFactory tf = new ThreadFactoryBuilder().setNameFormat("ZkClient childCacheUpdateExecutor %d ")
				.setDaemon(true).build();
		childCacheBackgroundExecutor = Executors.newSingleThreadExecutor(tf);
		long end = System.currentTimeMillis();
		log.info("connected to ZkServers " + zkServers + " in " + (end - start) + " ms.");
	}

	public void close() {
		client.close();
		childCacheBackgroundExecutor.shutdown();
		Set<Entry<String, PathChildrenCache>> entrySet = path2ChildrenCache.entrySet();
		for (Entry<String, PathChildrenCache> entry : entrySet) {
			try {
				entry.getValue().close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		path2ChildrenCache.clear();
	}

	public void create(String path, byte[] payload, boolean recursive) throws Exception {
		create(path, payload, recursive, CreateMode.PERSISTENT);
	}

	public void create(String path, byte[] payload) throws Exception {
		create(path, payload, false, CreateMode.PERSISTENT);
	}

	public void createEphemeral(String path, byte[] payload) throws Exception {
		create(path, payload, false, CreateMode.EPHEMERAL);
	}

	public void createEphemeral(String path, byte[] payload, boolean recursive) throws Exception {
		create(path, payload, recursive, CreateMode.EPHEMERAL);
	}

	private void create(String path, byte[] payload, boolean recursive, CreateMode mode) throws Exception {
		if (recursive) {
			client.create().creatingParentsIfNeeded().withMode(mode).forPath(path, payload);
		} else {
			client.create().withMode(mode).forPath(path, payload);
		}
	}

	public void setData(String path, byte[] payload) throws Exception {
		// set data for the given node
		client.setData().forPath(path, payload);
	}

	public void setDataAsync(String path, byte[] payload) throws Exception {
		// this is one method of getting event/async notifications
		CuratorListener listener = new CuratorListener() {
			@Override
			public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
				// examine event for details
			}
		};
		client.getCuratorListenable().addListener(listener);
		// set data for the given node asynchronously. The completion
		// notification
		// is done via the CuratorListener.
		client.setData().inBackground().forPath(path, payload);
	}

	public void setDataAsyncWithCallback(BackgroundCallback callback, String path, byte[] payload) throws Exception {
		// this is another method of getting notification of an async completion
		client.setData().inBackground(callback).forPath(path, payload);
	}

	public void delete(String path) throws Exception {
		// delete the given node
		client.delete().forPath(path);
	}

	public boolean exist(String path) throws Exception {
		Stat stat = client.checkExists().forPath(path);
		return stat == null;
	}

	public void eusureExist(String path) throws Exception {
		Stat stat = client.checkExists().forPath(path);
		if (stat == null) {
			create(path, null, true);
		}
	}

	public void eusureNotExist(String path) throws Exception {
		Stat stat = client.checkExists().forPath(path);
		if (stat != null) {
			guaranteedDelete(path);
		}
	}

	public void guaranteedDelete(String path) throws Exception {
		// delete the given node and guarantee that it completes
		client.delete().guaranteed().forPath(path);
	}

	public List<String> getChildren(String path) throws Exception {
		PathChildrenCache childCache = path2ChildrenCache.get(path);
		if (childCache == null) {
			childCache = new PathChildrenCache(client, path, true, false, childCacheBackgroundExecutor);
			childCache.start(StartMode.BUILD_INITIAL_CACHE);
			path2ChildrenCache.put(path, childCache);
		}

		List<ChildData> children = childCache.getCurrentData();
		if (CollectionUtils.isEmpty(children)) {
			return Collections.emptyList();
		}

		List<String> childPathList = new ArrayList<String>();
		for (ChildData child : children) {
			childPathList.add(child.getPath());
		}

		return childPathList;
	}

	public List<String> getChildren(String path, Watcher watcher) throws Exception {
		/**
		 * Get children and set the given watcher on the node.
		 */
		return client.getChildren().usingWatcher(watcher).forPath(path);
	}

	public byte[] getData(String path, Watcher watcher) throws Exception {
		return client.getData().usingWatcher(watcher).forPath(path);
	}

	public byte[] getData(String path) throws Exception {
		return client.getData().forPath(path);
	}
}
