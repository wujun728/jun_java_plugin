package com.github.ghsea.rpc.common.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.curator.utils.ZKPaths;

import com.github.ghsea.rpc.common.modle.BaseProfile;
import com.github.ghsea.rpc.common.modle.NodeId;
import com.github.ghsea.rpc.common.modle.ServiceProfile;
import com.google.common.base.Splitter;

/*
 * "/root/pool/service-version/ip:port"
 * "/SOA/groupon-data/qianggouQueryHandle-1.0-guhai/192.169.1.100:8090"  XXXXX
 * /SOA/groupon-data/qianggouQueryHandle-1.0-guhai/192.169.1.100:8090"   --->weigth:1
 * 
 * 关于group:
 * /root/pool/group
 * 
 */
public class PathUtil {

	public static final String ZK_ROOT = "/SOA";

	public static final String ZK_GROUP_ROOT = "/SOA-Group";

	public static final String SERVICE_VERSION_SEPARATOR = "-";

	public static final String HOST_PORT_SEPARATOR = ":";
	private static Splitter CHILD_PATH_SPLITTER = Splitter.on(HOST_PORT_SEPARATOR).trimResults();

	public static ServiceProfile getServiceProfile(String fullZkPath) {
		List<String> splittedPath = ZKPaths.split(fullZkPath);
		String pool = splittedPath.get(1);

		String serviceAndVersion = splittedPath.get(2);
		int idx = StringUtils.indexOf(serviceAndVersion, SERVICE_VERSION_SEPARATOR);
		String service = StringUtils.substring(serviceAndVersion, 0, idx);
		String version = StringUtils.substring(serviceAndVersion, idx);

		String node = splittedPath.get(3);
		List<String> childPathSplitted = CHILD_PATH_SPLITTER.splitToList(node);
		String ip = childPathSplitted.get(0);
		int port = Integer.parseInt(childPathSplitted.get(1));
		NodeId nodeId = new NodeId(ip, port);
		ServiceProfile sp = new ServiceProfile(pool, service, version, nodeId);
		return sp;
	}

	public static NodeId getNodeId(String zkNode) {
		List<String> childPathSplitted = CHILD_PATH_SPLITTER.splitToList(zkNode);
		NodeId node = new NodeId();
		node.setHost(childPathSplitted.get(0));
		node.setPort(Integer.parseInt(childPathSplitted.get(1)));
		return node;
	}

	/**
	 * 获取全路径<br>
	 * SOA/groupon-data/qianggouQueryHandle/1.0-guhai:192.169.1.100:8090
	 * 
	 * @param profile
	 * @return
	 */
	public static String getFullZkPath(ServiceProfile profile) {
		String basePath = getServiceZkPath((BaseProfile) profile);
		StringBuffer sb = new StringBuffer(basePath.length() + 40);
		NodeId node = profile.getNodeId();
		sb.append(basePath).append(ZKPaths.PATH_SEPARATOR).append(node.getHost()).append(HOST_PORT_SEPARATOR)
				.append(node.getPort());
		return sb.toString();
	}

	/**
	 * 获取截止于service Name的节点路径：/SOA/groupon-data/qianggouQueryHandle
	 * 
	 * @param profile
	 * @return
	 */
	public static String getServiceZkPath(BaseProfile profile) {
		StringBuffer sb = new StringBuffer();
		sb.append(ZK_ROOT).append(ZKPaths.PATH_SEPARATOR).append(profile.getPool()).append(ZKPaths.PATH_SEPARATOR)
				.append(profile.getService()).append(SERVICE_VERSION_SEPARATOR).append(profile.getVersion());
		return sb.toString();
	}

	/**
	 * 获取分组的zk路径 /root/pool/group
	 * 
	 * @param pool
	 * @param group
	 * @return
	 */
	public static String getGroupZkPath(String pool, String group) {
		StringBuffer sb = new StringBuffer();
		sb.append(ZK_GROUP_ROOT).append(ZKPaths.PATH_SEPARATOR).append(pool).append(ZKPaths.PATH_SEPARATOR)
				.append(group);
		return sb.toString();
	}

	public static String getGroupAndNodeZkPath(String pool, String group, NodeId node) {
		String groupZkPath = getGroupZkPath(pool, group);
		StringBuffer sb = new StringBuffer(groupZkPath.length() + 10);
		sb.append(groupZkPath).append(ZKPaths.PATH_SEPARATOR).append(node.getHost()).append(HOST_PORT_SEPARATOR)
				.append(node.getPort());
		return sb.toString();
	}
}
