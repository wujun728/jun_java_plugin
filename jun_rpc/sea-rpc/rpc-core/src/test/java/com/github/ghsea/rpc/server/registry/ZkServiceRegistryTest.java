package com.github.ghsea.rpc.server.registry;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import com.github.ghsea.rpc.client.locator.ZkServiceLocatorImpl;
import com.github.ghsea.rpc.common.modle.NodeId;
import com.github.ghsea.rpc.common.modle.ReferenceProfile;
import com.github.ghsea.rpc.common.modle.ReferenceProfile.ClientProfileBuilder;
import com.github.ghsea.rpc.common.modle.ServiceProfile;
import com.github.ghsea.rpc.common.util.PathUtil;
import com.github.ghsea.rpc.common.util.ZkClient;

public class ZkServiceRegistryTest {

	private static ZkServiceRegistry registry;

	private static ZkClient client;

	String pool = "groupon-data";
	String group = "mobile";
	String version = "1.0-guhai";
	String service = "grouponQueryHandler";
	String host = "192.168.1.104";
	int port = 8080;

	@BeforeClass
	public static void setUp() throws Exception {
		// String zkServers =
		// "192.168.1.111:2181,192.168.1.111:2182,192.168.1.111:2183";
		client = ZkClient.getInstance();
		registry = new ZkServiceRegistry();
	}

	@AfterClass
	public static void down() throws Exception {
		registry.destroy();
	}

	@Test
	public void testRegister() throws Exception {
		NodeId nodeId = new NodeId(host, port);
		ServiceProfile profile = new ServiceProfile(pool, service, version, nodeId);
		registry.register(profile);
		String servicePath = PathUtil.getServiceZkPath(profile);
		// it's just to wait for the zk create operation completetion
		TimeUnit.SECONDS.sleep(2L);
		List<String> nodeList = client.getChildren(servicePath);
		NodeId node = new NodeId();
		node.setHost(host);
		node.setPort(port);
		String nodePath = PathUtil.getFullZkPath(profile);
		Assert.assertEquals(1L, nodeList.size());
		Assert.assertEquals(nodeList.get(0), nodePath);
	}

	@Test
	public void testUnrigster() throws Exception {
		NodeId nodeId = new NodeId(host, port);
		ServiceProfile profile = new ServiceProfile(pool, service, version, nodeId);
		String servicePath = PathUtil.getServiceZkPath(profile);
		registry.unregister(profile);
		TimeUnit.SECONDS.sleep(2L);
		List<String> nodeList = client.getChildren(servicePath);
		Assert.assertEquals(0L, nodeList.size());
	}

	@Test
	public void testAddNodeForGroup() throws Exception {
		String host = "192.168.1.104";
		int port = 8080;
		NodeId node = new NodeId();
		node.setHost(host);
		node.setPort(port);

		registry.addNodeForGroup(pool, group, node);
		List<String> nodeList = client.getChildren(PathUtil.getGroupZkPath(pool, group));
		String nodePath = PathUtil.getGroupAndNodeZkPath(pool, group, node);
		Assert.assertEquals(1L, nodeList.size());
		Assert.assertTrue(nodeList.contains(nodePath));
	}

	@Test
	public void testDeleteNodeForGroup() throws Exception {
		String host = "192.168.1.104";
		int port = 8080;
		NodeId node = new NodeId();
		node.setHost(host);
		node.setPort(port);

		registry.addNodeForGroup(pool, group, node);
		List<String> nodeList = client.getChildren(PathUtil.getGroupZkPath(pool, group));
		String nodePath = PathUtil.getGroupAndNodeZkPath(pool, group, node);
		Assert.assertEquals(1L, nodeList.size());
		Assert.assertTrue(nodeList.contains(nodePath));

		registry.deleteNodeForGroup(pool, group, node);
		boolean isExist = client.exist(nodePath);
		Assert.assertEquals(false, isExist);
	}

	@Test
	public void testListAndSubscribe() throws Exception {
		// prepare for test data;
		testUnrigster();
		testRegister();

		ClientProfileBuilder builder = new ClientProfileBuilder(pool, group, version, service);
		ReferenceProfile reference = builder.build();
		ZkServiceLocatorImpl locator = new ZkServiceLocatorImpl(reference);
		Method methodOfListAndSubscribe = ReflectionUtils.findMethod(ZkServiceLocatorImpl.class, "listAndSubscribe");
		ReflectionUtils.makeAccessible(methodOfListAndSubscribe);
		List<ServiceProfile> spList = (List<ServiceProfile>) methodOfListAndSubscribe.invoke(locator);
		Assert.assertTrue(spList != null && spList.size() > 0);
		System.out.println(spList);
	}
}
