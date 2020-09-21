/*   
 * Project: OSMP
 * FileName: ServiceListener.java
 * version: V1.0
 */
package com.osmp.http.client.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

/**
 * 服务监听器
 * 
 * @author wangkaiping
 *
 */
public class ServiceListener implements PathChildrenCacheListener {

	@Override
	public void childEvent(CuratorFramework client, PathChildrenCacheEvent event)
			throws Exception {
		switch (event.getType()) {
		case CHILD_ADDED:

			break;
		case CHILD_UPDATED:

			break;
		case CHILD_REMOVED:

			break;

		default:
			break;
		}

	}

}
