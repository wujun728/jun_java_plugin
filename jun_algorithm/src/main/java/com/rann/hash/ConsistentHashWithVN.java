package com.rann.hash;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 带虚拟结点的一致性Hash算法
 *
 * @author hztaoran
 */
public class ConsistentHashWithVN {
    /**
     * 待加入Hash环的服务器列表
     */
    private static String[] servers = {"192.168.0.0:111", "192.168.0.1:111", "192.168.0.2:111", "192.168.0.3:111",
            "192.168.0.4:111"};

    /**
     * 真实结点列表，考虑到服务器上线、下线的场景，即添加、删除的场景会比较频繁，这里使用LinkedList会更好
     */
    private static List<String> realNodes = new LinkedList<>();

    /**
     * key表示虚拟结点服务器的hash值，value表示虚拟结点服务器的名称
     */
    private static SortedMap<Integer, String> virtualNodes = new TreeMap<>();

    /**
     * 虚拟结点数目（一个真实结点对应VN_SUM个虚拟结点）
     */
    private static final int VN_SUM = 5;

    /**
     * 加所有服务器加入集合
     */
    static {
        for (int i = 0; i < servers.length; i++) {
            realNodes.add(servers[i]);
        }

        for (String str : realNodes) {
            for (int i = 0; i < VN_SUM; i++) {
                String virtualNodeName = str + "&VN" + String.valueOf(i);
                int hash = HashUtil.FNV1_32_HASH(virtualNodeName);
                System.out.println("虚拟节点[" + virtualNodeName + "]被添加, hash值为" + hash);
                virtualNodes.put(hash, virtualNodeName);
            }
        }
        System.out.println("\n===========路由映射==============\n");
    }

    private static String matchServer(String node) {
        // 待路由结点的Hash值
        int hash = HashUtil.FNV1_32_HASH(node);
        // 得到大于该Hash值的子Map
        SortedMap<Integer, String> subMap = virtualNodes.tailMap(hash);
        // 顺时针的第一个Key
        Integer i = subMap.firstKey();
        // 截取
        String virtualNode = subMap.get(i);

        // 返回路由到的服务器名称
        return virtualNode.substring(0, virtualNode.indexOf("&"));
    }

    public static void main(String[] args) {

        String[] nodes = {"127.0.0.1:1111", "221.226.0.1:2222", "10.211.0.1:3333", "112.74.15.218:80"};
        for (int i = 0; i < nodes.length; i++) {
            System.out.println(
                    "[" + nodes[i] + "]的hash值为" + HashUtil.FNV1_32_HASH(nodes[i]) + ",被路由到的服务器为[" + matchServer(nodes[i]) + "]");
        }
    }
}
