package com.github.ghsea.rpc.server.registry;

import org.apache.zookeeper.KeeperException.NoNodeException;
import org.apache.zookeeper.KeeperException.NodeExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Scope;

import com.alibaba.fastjson.JSON;
import com.github.ghsea.rpc.common.modle.NodeId;
import com.github.ghsea.rpc.common.modle.ServiceProfile;
import com.github.ghsea.rpc.common.util.PathUtil;
import com.github.ghsea.rpc.common.util.ZkClient;

/**
 * 基于Zookeeper的服务注册发现<br>
 * 服务节点的目录结构如下:<br>
 * "/SOA/groupon-data <br>
 * /qianggouQueryHandle-1.0-guhai/192.169.1.100:8090" <br>
 * /hourbuyQueryHandle-1.0-guhai/192.169.1.100:8090" <br>
 * 
 * 服务节点的目录结构如下:<br>
 * "/SOA-Group/"<br>
 * groupon-data/qianggou /192.169.1.100<br>
 * 
 * @author GuHai 2017-2-11上午11:21:21
 */
@Scope("singleton")
public class ZkServiceRegistry implements ServiceRegistry, DisposableBean {

	private Logger log = LoggerFactory.getLogger(ZkServiceRegistry.class);
	private ZkClient zkClient = ZkClient.getInstance();

	public ZkServiceRegistry() {
	}

	@Override
	public void destroy() throws Exception {
		zkClient.close();
	}

	@Override
	public void register(ServiceProfile profile) throws Exception {
		String servicePath = PathUtil.getServiceZkPath(profile);
		zkClient.eusureExist(servicePath);
		String fullPath = PathUtil.getFullZkPath(profile);
		zkClient.eusureNotExist(fullPath);
		zkClient.createEphemeral(fullPath, convert(profile));
	}

	@Override
	public void unregister(ServiceProfile profile) throws Exception {
		String fullPath = PathUtil.getFullZkPath(profile);
		try {
			zkClient.guaranteedDelete(fullPath);
		} catch (NoNodeException ex) {
			log.error(ex.getMessage(), ex);
		}
	}

	@Override
	public void update(ServiceProfile profile) throws Exception {
		byte[] data = convert(profile);
		String fullPath = PathUtil.getFullZkPath(profile);
		zkClient.setData(fullPath, data);
	}

	@Override
	public void addNodeForGroup(String pool, String group, NodeId node) throws Exception {
		String groupPath = PathUtil.getGroupZkPath(pool, group);
		zkClient.eusureExist(groupPath);
		String nodePath = PathUtil.getGroupAndNodeZkPath(pool, group, node);
		try {
			zkClient.create(nodePath, null);
		} catch (NodeExistsException ex) {
			log.error(ex.getMessage(), ex);
		}

	}

	@Override
	public void deleteNodeForGroup(String pool, String group, NodeId node) throws Exception {
		String groupPath = PathUtil.getGroupZkPath(pool, group);
		if (zkClient.exist(groupPath)) {
			String nodePath = PathUtil.getGroupAndNodeZkPath(pool, groupPath, node);
			zkClient.delete(nodePath);
		}
	}

	private byte[] convert(final ServiceProfile profile) {
		return JSON.toJSONBytes(profile);
	}

}
