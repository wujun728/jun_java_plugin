/**
 * 
 */
package com.opensource.nredis.proxy.monitor.zookeeper.utils;

import com.opensource.nredis.proxy.monitor.string.utils.StringUtil;
import com.opensource.nredis.proxy.monitor.zookeeper.constants.NRedisProxyConstants;
import com.opensource.nredis.proxy.monitor.zookeeper.enums.ZkNodeType;


/**
 * ZK 路径工具类
 * @author liubing
 *
 */
public class ZkUtils {
	
	private static String toServicePath() {
        return NRedisProxyConstants.ZOOKEEPER_REGISTRY_NAMESPACE  ;
    }
	
	private static String toCommandPath() {
        return toServicePath() + NRedisProxyConstants.ZOOKEEPER_REGISTRY_COMMAND;
    }

	public static String toNodeTypePath( ZkNodeType nodeType) {
        String type;
        if (nodeType == ZkNodeType.AVAILABLE_SERVER) {
            type = "server";
        } else if (nodeType == ZkNodeType.UNAVAILABLE_SERVER) {
            type = "unavailableServer";
        } else if (nodeType == ZkNodeType.CLIENT) {
            type = "client";
        } else {
            throw new RuntimeException(String.format("Failed to get nodeTypePath, type: %s", nodeType.toString()));
        }
        return toCommandPath() + NRedisProxyConstants.PATH_SEPARATOR + type;
    }

    public static String toNodePath(String parentPath,String serverKey, ZkNodeType nodeType) {
    	if(StringUtil.isNotEmpty(parentPath)){
    		return toNodeTypePath( nodeType)+ NRedisProxyConstants.PATH_SEPARATOR+parentPath+NRedisProxyConstants.PATH_SEPARATOR +serverKey;
    	}
        return toNodeTypePath(nodeType)+ NRedisProxyConstants.PATH_SEPARATOR +serverKey;
    }
    
}
