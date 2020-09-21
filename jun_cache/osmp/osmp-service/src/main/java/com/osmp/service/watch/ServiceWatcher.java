/*   
 * Project: OSMP
 * FileName: ServiceWatcher.java
 * version: V1.0
 */
package com.osmp.service.watch;

import java.util.Date;
import java.util.UUID;

import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.osgi.context.BundleContextAware;
import org.springframework.util.Assert;

import com.osmp.intf.define.config.FrameConst;
import com.osmp.intf.define.interceptor.ServiceInterceptor;
import com.osmp.intf.define.service.BaseDataService;
import com.osmp.intf.define.service.ZookeeperService;
import com.osmp.service.bean.DataServiceInfo;
import com.osmp.service.bean.InterceptorInfo;
import com.osmp.service.factory.ServiceFactoryImpl;
import com.osmp.service.manager.ServiceStateManager;
import com.osmp.service.registration.ServiceContainer;
import com.osmp.service.util.ServiceUtil;
import com.osmp.utils.net.RequestInfoHelper;

/**
 * 
 * Description:服务注册、注销、监听
 * @author: wangkaiping
 * @date: 2016年8月9日 上午10:27:15上午10:51:30
 */
public class ServiceWatcher implements BundleContextAware, InitializingBean, DisposableBean {
	private Logger logger = LoggerFactory.getLogger(ServiceWatcher.class);

	private ServiceTracker dataServiceTracker;
	private ServiceTracker serviceInterceptorTracker;

	private ServiceStateManager serviceStateManager;
	private ServiceFactoryImpl serviceFactoryImpl;
	private BundleContext context;
	private ZookeeperService zookeeper;
	private final static String NODE_CHANGE = "/osmp/nodechange";

	@Override
	public void setBundleContext(BundleContext context) {
		this.context = context;
	}

	public void setServiceFactoryImpl(ServiceFactoryImpl serviceFactoryImpl) {
		this.serviceFactoryImpl = serviceFactoryImpl;
	}

	public void setServiceStateManager(ServiceStateManager serviceStateManager) {
		this.serviceStateManager = serviceStateManager;
	}

	public void setZookeeper(ZookeeperService zookeeper) {
		this.zookeeper = zookeeper;
	}

	@Override
	public void destroy() throws Exception {
		if (dataServiceTracker != null) {
			dataServiceTracker.close();
		}
		if (serviceInterceptorTracker != null) {
			serviceInterceptorTracker.close();
		}

		logger.info("服务监听结束");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(context);
		Assert.notNull(serviceStateManager);
		Assert.notNull(serviceFactoryImpl);
		dataServiceTracker = new ServiceTracker(context, BaseDataService.class.getName(),
				new DataServiceTrackerCustomizer());
		serviceInterceptorTracker = new ServiceTracker(context, ServiceInterceptor.class.getName(),
				new ServiceInterceptorTrackerCustomizer());

		dataServiceTracker.open(true);
		serviceInterceptorTracker.open(true);

		context.addBundleListener(new BundleListener() {

			@Override
			public void bundleChanged(BundleEvent event) {
				if (event.getType() == BundleEvent.UNINSTALLED) {
					String name = event.getBundle().getSymbolicName();
					try {
						logger.info("uninstall bundle " + name);
						zookeeper.deleteNodeByBundle(name);
						ServiceWatcher.this.nodeUpdate();
					} catch (Exception e) {
						logger.error(
								"zookeeper delete service by bundle, bundle name : "
										+ name, e);
					}
				}
			}
		});

		logger.info("服务监听启动");
	}

	// dataService监听
	private class DataServiceTrackerCustomizer implements ServiceTrackerCustomizer {
		@Override
		public Object addingService(ServiceReference reference) {
			BaseDataService bsService = (BaseDataService) context.getService(reference);
			String bundleName = reference.getBundle().getSymbolicName();
			String bundleVersion = reference.getBundle().getVersion().toString();
			Object name = reference.getProperty(FrameConst.SERVICE_NAME);
			if (name == null || "".equals(name)) {
				logger.error("组件" + bundleName + "(" + bundleVersion
						+ ") dataService服务name未设置");
				return bsService;
			}
			Object mark = reference.getProperty(FrameConst.SERVICE_MARK);
			ServiceContainer.getInstance().putDataService(bundleName, bundleVersion, name.toString(), bsService);
			DataServiceInfo info = new DataServiceInfo();
			info.setBundle(bundleName);
			info.setVersion(bundleVersion);
			info.setName(name.toString());
			info.setState(1);
			info.setUpdateTime(new Date());
			info.setMark(mark == null ? "" : mark.toString());
			serviceStateManager.updateDataService(info);
			String path = ZookeeperService.ROOT_PATH
					+ ZookeeperService.SERVICE + "/"
					+ RequestInfoHelper.getLocalIp() + "/";
			logger.debug("register service path: " + path + " bundle : " + bundleName + " to zookeeper ");
			ServiceWatcher.this.registerService(path, info);
			return bsService;
		}

		@Override
		public void modifiedService(ServiceReference reference, Object service) {
		}

		@Override
		public void removedService(ServiceReference reference, Object service) {
			String bundleName = reference.getBundle().getSymbolicName();
			String bundleVersion = reference.getBundle().getVersion()
					.toString();
			Object name = reference.getProperty(FrameConst.SERVICE_NAME);
			if (name == null || "".equals(name)) {
				logger.error("组件" + bundleName + "(" + bundleVersion
						+ ") dataService服务name未设置");
				return;
			}
			ServiceContainer.getInstance().removeDataService(bundleName, bundleVersion, name.toString());
			Object mark = reference.getProperty(FrameConst.SERVICE_MARK);
			DataServiceInfo info = new DataServiceInfo();
			info.setBundle(bundleName);
			info.setVersion(bundleVersion);
			info.setName(name.toString());
			info.setState(0);
			info.setMark(mark == null ? "" : mark.toString());
			info.setUpdateTime(new Date());
			serviceStateManager.updateDataService(info);
			System.out.println("===============remove service bundleName:"
					+ bundleName + " name: " + name.toString() + " mark: "
					+ mark.toString());
			String path = ZookeeperService.ROOT_PATH
					+ ZookeeperService.SERVICE + "/"
					+ RequestInfoHelper.getLocalIp() + "/";
			ServiceWatcher.this.unRegisterService(path, info);
		}

	}

	// ServiceInterceptor监听
	private class ServiceInterceptorTrackerCustomizer implements ServiceTrackerCustomizer {
		@Override
		public Object addingService(ServiceReference reference) {
			ServiceInterceptor sicpt = (ServiceInterceptor) context.getService(reference);
			String bundleName = reference.getBundle().getSymbolicName();
			String bundleVersion = reference.getBundle().getVersion().toString();
			Object name = reference.getProperty(FrameConst.SERVICE_NAME);
			if (name == null || "".equals(name)) {
				logger.error("组件" + bundleName + "(" + bundleVersion
						+ ") serviceInterceptor服务name未设置");
				return sicpt;
			}
			Object mark = reference.getProperty(FrameConst.SERVICE_MARK);
			ServiceContainer.getInstance().putInterceptor(
					ServiceUtil.generateServiceName(bundleName, bundleVersion, name.toString()), sicpt);
			InterceptorInfo info = new InterceptorInfo();
			info.setBundle(bundleName);
			info.setVersion(bundleVersion);
			info.setName(name.toString());
			info.setState(1);
			info.setUpdateTime(new Date());
			info.setMark(mark == null ? "" : mark.toString());
			serviceStateManager.updateServiceInterceptor(info);
			return sicpt;
		}

		@Override
		public void modifiedService(ServiceReference reference, Object service) {
		}

		@Override
		public void removedService(ServiceReference reference, Object service) {
			String bundleName = reference.getBundle().getSymbolicName();
			String bundleVersion = reference.getBundle().getVersion().toString();
			Object name = reference.getProperty(FrameConst.SERVICE_NAME);
			if (name == null || "".equals(name)) {
				logger.error("组件" + bundleName + "(" + bundleVersion
						+ ") serviceInterceptor服务name未设置");
				return;
			}
			Object mark = reference.getProperty(FrameConst.SERVICE_MARK);
			ServiceContainer.getInstance().removeInterceptor(
					ServiceUtil.generateServiceName(bundleName, bundleVersion, name.toString()));
			InterceptorInfo info = new InterceptorInfo();
			info.setBundle(bundleName);
			info.setVersion(bundleVersion);
			info.setName(name.toString());
			info.setState(0);
			info.setMark(mark == null ? "" : mark.toString());
			info.setUpdateTime(new Date());
			serviceStateManager.updateServiceInterceptor(info);
		}

	}

	/**
	 * 向zookeeper注册服务
	 * 
	 * @param path
	 * @param ds
	 */
	public void registerService(String path, DataServiceInfo ds) {
		String bundle = ds.getBundle();
		String cname = ds.getMark();
		String name = ds.getName();
		String version = ds.getVersion();
		String status = String.valueOf(ds.getState());
		try {
			if (!zookeeper.exists(path + name)) {
				zookeeper.createNode(path + name);
			}
			if (!zookeeper.exists(path + name + "/bundle")) {
				zookeeper.createNode(path + name + "/bundle", bundle);
			} else {
				zookeeper.setNodeData(path + name + "/bundle", bundle);
			}
			if (!zookeeper.exists(path + name + "/cname")) {
				zookeeper.createNode(path + name + "/cname", cname);
			} else {
				zookeeper.setNodeData(path + name + "/cname", cname);
			}
			if (!zookeeper.exists(path + name + "/version")) {
				zookeeper.createNode(path + name + "/version", version);
			} else {
				zookeeper.setNodeData(path + name + "/version", version);
			}
			if (!zookeeper.exists(path + name + "/status")) {
				zookeeper.createNode(path + name + "/status", status);
			} else {
				zookeeper.setNodeData(path + name + "/status", status);
			}
			this.nodeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("zookeeper register service fail, service name : "
					+ name, e);
		}
	}

	/**
	 * 卸载服务
	 * 
	 * @param path
	 * @param ds
	 */
	public void unRegisterService(String path, DataServiceInfo ds) {
		String name = ds.getName();
		try {
			if (zookeeper.exists(path + name + "/status")) {
				zookeeper.setNodeData(path + name + "/status",
						String.valueOf(ds.getState()));
				this.nodeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("zookeeper unRegister service fail, service name : "
					+ name, e);
		}
	}
	
	/**
	 * 更新节点状态变化
	 */
	public void nodeUpdate(){
		String data = UUID.randomUUID().toString();
		try {
			if(zookeeper.exists(NODE_CHANGE)){
				zookeeper.setNodeData(NODE_CHANGE, data);
			}else{
				zookeeper.createNode(NODE_CHANGE);
				zookeeper.setNodeData(NODE_CHANGE, data);
			}
		} catch (Exception e) {
			logger.error("更新节点变化状态错误", e);
		}
	}

}
