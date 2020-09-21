/*   
 * Project: OSMP
 * FileName: ZookeeperService.java
 * version: V1.0
 */
package com.osmp.http.client.zookeeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * Description: 客户端APP ZOOKEEPER SDK
 *		主要提供客户端请求的软负载、服务路由、策略路由、灰度发布等功能。
 *		从注册中心获取服务注册情况，服务器激活情况，策略发布情况，配置情况，性能情况进行综合的负载均衡算法。
 * @author: wangkaiping
 * @date: 2015年11月09日 下午2:09:05
 */
public class ZookeeperService {
	
	private static final Logger logger = Logger.getLogger(ZookeeperService.class);

	/**
	 * 服务列表 key服务名称 list 服务器IP
	 */
	public static Map<String, List<String>> smap = new ConcurrentHashMap<String, List<String>>();

	/**
	 * 注册根节点
	 */
	public static final String ROOT_PATH = "/osmp";

	/**
	 * 注册服务节点
	 */
	public static final String SERVICE = "/service";

	/**
	 * 注册服务器状态节点
	 */
	public static final String STATUS = "/status";

	/**
	 * 注册策略节点
	 */
	public static final String STRATEGY = "/strategy";

	/**
	 * 注册性能统计节点
	 */
	public static final String COUNT = "/count";
	
	/**
	 * 节点变化状态节点
	 */
	public static final String NODE_CHANGE = "/osmp/nodechange";

	/**
	 * zk 连接URL地址
	 */
	public String url;

	/**
	 * zk客户端
	 */
	private static CuratorFramework client;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * 节点是否存在
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public boolean exists(String path) throws Exception {
		Stat stat = client.checkExists().forPath(path);
		return !(stat == null);
	}
	
	/**
	 * 创建永久节点
	 * @param path
	 * @param data
	 * @throws Exception
	 */
	public void createNode(String path, String data) throws Exception {
		client.create().creatingParentsIfNeeded()
				.withMode(CreateMode.PERSISTENT).forPath(path, data.getBytes());

	}

	/**
	 * 获取节点数据
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public String getNodeDate(String path) throws Exception {
		byte[] content = client.getData().forPath(path);
		String str = new String(content);
		return str;
	}
	
	public void deleteNode(String path) throws Exception{
		client.delete().deletingChildrenIfNeeded().forPath(path);
	}
	
	/**
	 * 获取激活服务器列表
	 * @return
	 * @throws Exception 
	 */
	public static List<String> getActiveServer() throws Exception{
		String path = ZookeeperService.ROOT_PATH+ZookeeperService.STATUS;
		client.sync().forPath(path);
		return client.getChildren().forPath(path);
	}

	/**
	 * 初始化客户端并添加监听器
	 * 
	 * @throws Exception
	 */
	public void init() throws Exception {
		ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 1);
		client = CuratorFrameworkFactory.newClient(url, retryPolicy);
		client.start();
		
//		this.addListener();
//		this.addServiceListener();
//		this.addServiceStatusListener();
		this.addNodeUpdataListener();
		this.loadConfig();
	}

	/**
	 * 添加子节点监听器
	 * 
	 * @param path
	 *            监听节点路径
	 * @param isSubNode
	 *            是否监听子节点
	 * @throws Exception
	 */
	protected void addPathChildrenListener(String path, PathChildrenCacheListener listener) throws Exception {
		PathChildrenCache cache = new PathChildrenCache(client, path, true);
		cache.start(StartMode.POST_INITIALIZED_EVENT);
		cache.getListenable().addListener(listener);
	}

	/**
	 * 得到子节点
	 * 
	 * @return
	 * @throws Exception
	 */
	protected List<String> getChildPath(String path) throws Exception {
		return client.getChildren().forPath(path);
	}
	
	/**
	 * 新加入服务器时的监听
	 * @throws Exception
	 */
	protected void addListener() throws Exception{
		String path = ZookeeperService.ROOT_PATH + ZookeeperService.SERVICE;
		PathChildrenCache cache = new PathChildrenCache(client, path, true);
		cache.start(StartMode.POST_INITIALIZED_EVENT);
		cache.getListenable().addListener(new PathChildrenCacheListener() {
			@Override
			public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
				if(PathChildrenCacheEvent.Type.CHILD_ADDED.equals(event.getType())){
					ZookeeperService.this.addPathChildrenListener(event.getData().getPath(), new PathChildrenCacheListener(){
						@Override
						public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
							String subPath = event.getData().getPath();
							subPath = subPath+"/status";
							ZookeeperService.this.addNodeChangedListener(subPath);//给状态加节点内容变化的监听器
						}
					});
				}
			}
		});
	}

	/**
	 * 添加服务监听器 简单粗暴的直接重新RELOAD一次。不再去详细的监听每一个节点的详细内容
	 * 
	 * @throws Exception
	 */
	protected void addServiceListener() throws Exception {
		String path = ZookeeperService.ROOT_PATH + ZookeeperService.SERVICE;
		List<String> childPath = this.getChildPath(path);
		if (null != childPath && childPath.size() > 0) {
			for (final String i : childPath) {// 服务器列表
				final String temp = path + "/" + i;
				System.out.println(temp);
				this.addPathChildrenListener(temp, new PathChildrenCacheListener() {
					@Override
					public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
						System.out.println("+++++++服务++++++++"+event.getData().getPath());
						
//						String sname = event.getData().getPath().replaceAll(temp, "").replaceAll("/", "");// 服务名称
//						List<String> list = ZookeeperService.smap.get(sname);// 服务器IP列表
//						switch (event.getType()) {
//						case CHILD_ADDED:// 新增子节点
//							if (null == list) {
//								List<String> temp = new ArrayList<String>();
//								temp.add(i);
//								ZookeeperService.smap.put(sname, temp);
//							} else if (!list.contains(i)) {
//								list.add(i);
//							}
//							break;
//						case CHILD_REMOVED:// 删除子节点
//							if (null != list && list.contains(i)) {
//								list.remove(i);
//							}
//							break;
//						default:
//							break;
//						}
						ZookeeperService.this.loadConfig();
					}
				});
			}
		}
	}
	
	/**
	 * 给节点增加内容变化的监听器
	 * @param path
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	protected void addNodeChangedListener(String path) throws Exception{
		final NodeCache cache = new NodeCache(client, path, false);
		cache.start(true);
		cache.getListenable().addListener(new NodeCacheListener(){

			@Override
			public void nodeChanged() throws Exception {
				// TODO Auto-generated method stub
				String t = cache.getCurrentData().getPath();
				System.out.println("---------------"+t);
				ZookeeperService.this.loadConfig();
			}
			
		});
	}

	/**
	 * 服务状态监听器 简单粗暴的直接重新RELOAD一次。不再去详细的监听每一个节点的详细内容
	 * 
	 * @throws Exception
	 */
	protected void addServiceStatusListener() throws Exception {
		String path = ZookeeperService.ROOT_PATH + ZookeeperService.SERVICE;
		List<String> childPath = this.getChildPath(path);
		if (null != childPath && childPath.size() > 0) {
			for (final String i : childPath) {// 服务器列表
				String temp = path + "/" + i;
				List<String> spath = this.getChildPath(temp);
				if(null != spath && spath.size() > 0){
					for (final String j : spath) {// 服务列表
						String status = temp + "/" + j + "/status";
						this.addNodeChangedListener(status);
					}
				}
			}
		}

	}
	
	/**
	 * 添加状态改变结节监听器
	 * @throws Exception 
	 */
	protected void addNodeUpdataListener() throws Exception{
		if(!this.exists(ZookeeperService.NODE_CHANGE)){
			String data = UUID.randomUUID().toString();
			this.createNode(ZookeeperService.NODE_CHANGE, data);
		}
		this.addNodeChangedListener(ZookeeperService.NODE_CHANGE);
	}

	/**
	 * 加载配置
	 * 
	 * @param path
	 * @throws Exception
	 */
	public synchronized void loadConfig() throws Exception {
		ZookeeperService.smap.clear();
		String path = ZookeeperService.ROOT_PATH + ZookeeperService.SERVICE;
		client.sync().forPath(path);
		List<String> childPath = this.getChildPath(path);
		if (null != childPath && childPath.size() > 0) {
			for (String i : childPath) {// 服务器列表 10.34.39.112
				String server = path + "/" + i;
				List<String> serviceList = this.getChildPath(server);
				if(null != serviceList && serviceList.size() > 0){
					for (String j : serviceList) {// 服务列表 snbidLogin
						String services = server + "/" + j + "/status";
						String status = this.getNodeDate(services);// 服务状态
						if ("1".equals(status)) {// 服务状态激活的情况下加入服务列表
							List<String> list = ZookeeperService.smap.get(j);
							if (null == list || list.size() == 0) {// 还没有服务列表
								List<String> temp = new ArrayList<String>();
								temp.add(i);
								ZookeeperService.smap.put(j, temp);
							} else {// 已经有服务列表，在其它服务器上有同样的服务
								if (!list.contains(i)) {
									list.add(i);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 根据服务获取服务器地址（根据注册中心的配置实现服务的软负载、服务路由、灰度发布计算等） 
	 * 1、根据各服务器上的服务调用情况
	 * 2、根据采集到的CPU、MEMORT等指标情况考虑 
	 * 3、根据ZK上的配置的权重 
	 * 4、根据网络耗时
	 * 		PS：目前实现最简单的(服务器DOWN掉自动切换多服务器随机发送)
	 * @param service
	 * @return
	 * @throws Exception 
	 */
	public static String getServiceAdd(String service){
		List<String> list = ZookeeperService.smap.get(service);
		
		List<String> serverList;
		try {
			serverList = ZookeeperService.getActiveServer();
			if(null != serverList && null != list){
				list.retainAll(serverList);
			}
		} catch (Exception e) {
			logger.error("zookeeper server is down ... ", e);
		}
		
		//TODO 随机算法(暂未考虑以上四种情况的负载均衡算法)
		if (null != list && list.size() > 0) {
			int index = (int) (Math.random() * list.size());
			String rand = list.get(index);
			return rand;
		} else {
			return null;
		}
	}

	
	
	public static void main(String[] args) throws Exception {
		ZookeeperService zs = new ZookeeperService();
		zs.setUrl("10.34.39.112:2181,10.34.39.113:2181,10.34.39.114:2181");
		zs.init();
		int index = 0;
		while (index < 500) {
			Thread.sleep(5 * 1000);
			System.out.println(ZookeeperService.smap);
			System.out.println(ZookeeperService.getServiceAdd("snbidLogin"));
			index++;
		}
		
	}

}
