package com.github.ghsea.rpc.client.locator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.github.ghsea.rpc.client.locator.loadbalancer.LoadBalanceFactory;
import com.github.ghsea.rpc.client.locator.loadbalancer.LoadBalancer;
import com.github.ghsea.rpc.common.modle.NodeId;
import com.github.ghsea.rpc.common.modle.ReferenceProfile;
import com.github.ghsea.rpc.common.modle.ServiceProfile;
import com.github.ghsea.rpc.common.util.PathUtil;
import com.github.ghsea.rpc.common.util.ZkClient;

public class ZkServiceLocatorImpl implements ServiceLocator {

	private static Logger log = LoggerFactory.getLogger(ZkServiceLocatorImpl.class);

	private ReferenceProfile reference;

	private final LoadBalancer loadBalancer;

	private ZkClient zkClient;

	public ZkServiceLocatorImpl(ReferenceProfile reference) {
		this.reference = reference;
		this.zkClient = ZkClient.getInstance();
		loadBalancer = LoadBalanceFactory.getLoadBalance(reference);
		init();
	}

	@Override
	public ServiceProfile locateService() {
		return loadBalancer.select();
	}

	private void init() {
		log.debug("Client ZkServiceLocatorImpl init for {} ", JSON.toJSONString(reference));
		try {
			List<ServiceProfile> spList = listAndSubscribe();
			String pool = reference.getPool();
			String group = reference.getGroup();
			List<NodeId> nodeList = listNodeForGroup(pool, group, true);
			loadBalancer.init(spList, nodeList);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 列出service下的所有机器节点
	 * 
	 * @return
	 * @throws Exception
	 */
	private List<ServiceProfile> listAndSubscribe() throws Exception {
		String basePath = PathUtil.getServiceZkPath(reference);
//		final String pool = reference.getPool();
//		final String group = reference.getGroup();
		List<String> nodeList = zkClient.getChildren(basePath, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				EventType type = event.getType();
				switch (type) {
				case NodeCreated:
					//
					break;
				case NodeChildrenChanged:
					// 该节点已经是最下一层节点，它不应该再有子节点
//					List<ServiceProfile> spList;
//					List<NodeId> nodeList;
//					try {
//						spList = listAndSubscribe();
//						nodeList = listNodeForGroup(pool, group, false);
//					} catch (Exception e) {
//						throw new RuntimeException(e);
//					}
//					loadBalancer.init(spList, nodeList);
					init();
					break;
				case NodeDataChanged:
					log.warn("Zk NodeDataChanged:{}", event.getPath());
					break;
				case NodeDeleted:
					log.warn("Zk NodeDeleted:{}", event.getPath());
					break;
				default:
					;
				}
			}
		});

		List<ServiceProfile> spList = new ArrayList<ServiceProfile>();
		for (String zkNode : nodeList) {
			byte[] data = zkClient.getData(basePath + ZKPaths.PATH_SEPARATOR + zkNode);
			ServiceProfile sp = convert(data);
			spList.add(sp);
		}

		log.debug("Rpc Client listAndSubscribe result :{}", JSON.toJSONString(spList));

		return spList;
	}

	/**
	 * 根据Pool和分组group列出其下的所有机器
	 * 
	 * @param pool
	 * @param group
	 * @return
	 */
	private List<NodeId> listNodeForGroup(final String pool, final String group, boolean watch) throws Exception {
		String path = PathUtil.getGroupZkPath(pool, group);

		List<String> nodePathList = null;
		if (watch) {
			nodePathList = zkClient.getChildren(path, new Watcher() {
				@Override
				public void process(WatchedEvent event) {
					EventType type = event.getType();
					switch (type) {
					case NodeCreated:
						//
						break;
					case NodeChildrenChanged:
						// 该节点已经是最下一层节点，它不应该再有子节点
						List<NodeId> nodeList;
						try {
							nodeList = listNodeForGroup(pool, group, true);
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
						loadBalancer.update(nodeList);
						break;
					case NodeDataChanged:
						break;
					case NodeDeleted:
						break;
					default:
						;
					}
				}
			});
		} else {
			nodePathList = zkClient.getChildren(path);
		}

		if (CollectionUtils.isEmpty(nodePathList)) {
			log.debug("Rpc Client listNodeForGroup result :{}", JSON.toJSONString(nodePathList));
			return Collections.emptyList();
		}

		List<NodeId> nodeList = new ArrayList<NodeId>(nodePathList.size());
		for (String node : nodePathList) {
			nodeList.add(PathUtil.getNodeId(node));
		}

		log.debug("Rpc Client listNodeForGroup result :{}", JSON.toJSONString(nodePathList));
		return nodeList;
	}

	private ServiceProfile convert(byte[] data) {
		return JSON.parseObject(new String(data), ServiceProfile.class);
	}

}
