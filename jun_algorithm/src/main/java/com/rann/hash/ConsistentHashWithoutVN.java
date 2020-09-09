package com.rann.hash;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 不带虚拟结点的一致性Hash算法
 * @author 哓哓
 *
 */
public class ConsistentHashWithoutVN {

	/**
	 * 待加入Hash环的服务器列表
	 */
	private static String[] servers = { "192.168.0.0:111", "192.168.0.1:111", "192.168.0.2:111", "192.168.0.3:111",
			"192.168.0.4:111" };

	/**
	 * key表示服务器的hash值，value表示服务器的名称
	 */
	private static SortedMap<Integer, String> sortedMap = new TreeMap<>();

	/**
	 * 程序初始化，将所有服务器加入集合
	 */
	static {
		for (int i = 0; i < servers.length; i++) {
			int hash = getHash(servers[i]);
			 System.out.println("[" + servers[i] + "]加入集群中, 其Hash值为" + hash);
		     sortedMap.put(hash, servers[i]);
		}
	}

	/**
	 * 使用FNV1_32_HASH算法计算hash值
	 * @param str
	 * @return
	 */
	private static int getHash(String str) {
		final int p = 16777619;
		int hash = (int) 2166136261L;
		for (int i = 0; i < str.length(); i++) {
			hash = (hash ^ str.charAt(i)) * p;
		}
		hash += hash << 13;
		hash ^= hash >> 7;
		hash += hash << 3;
		hash ^= hash >> 17;
		hash += hash << 5;

		// 如果算出来的值为负数则取其绝对值
		if (hash < 0)
			hash = Math.abs(hash);
		return hash;
	}
	
	private static String matchServer(String node) {
		// 待路由结点的Hash值
		int hash = getHash(node);
		// 得到大于该Hash值的子Map
		SortedMap<Integer, String> subMap = sortedMap.tailMap(hash);
		// 顺时针的第一个Key
		Integer i = subMap.firstKey();
		// 返回路由到的服务器名称
		return subMap.get(i);
	}
	
	public static void main(String[] args) {
		 
		String[] nodes = {"127.0.0.1:1111", "221.226.0.1:2222", "10.211.0.1:3333"};
		for (int i = 0; i < nodes.length; i++) {
			System.out.println("[" + nodes[i] + "]的hash值为" + getHash(nodes[i]) + ",被路由到的服务器为[" 
		+ matchServer(nodes[i]) + "]");
		}
	}
	
}
