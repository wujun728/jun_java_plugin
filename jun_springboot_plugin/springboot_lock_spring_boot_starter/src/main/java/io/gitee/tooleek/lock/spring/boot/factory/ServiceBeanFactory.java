package io.gitee.tooleek.lock.spring.boot.factory;

import java.util.EnumMap;

import io.gitee.tooleek.lock.spring.boot.enumeration.LockType;
import io.gitee.tooleek.lock.spring.boot.exception.ServiceNotFoundException;
import io.gitee.tooleek.lock.spring.boot.service.LockService;
import io.gitee.tooleek.lock.spring.boot.service.impl.FairLockServiceImpl;
import io.gitee.tooleek.lock.spring.boot.service.impl.MultiLockServiceImpl;
import io.gitee.tooleek.lock.spring.boot.service.impl.ReadLockServiceImpl;
import io.gitee.tooleek.lock.spring.boot.service.impl.RedLockServiceImpl;
import io.gitee.tooleek.lock.spring.boot.service.impl.ReentrantLockServiceImpl;
import io.gitee.tooleek.lock.spring.boot.service.impl.WriteLockServiceImpl;
import io.gitee.tooleek.lock.spring.boot.util.SpringUtil;

/**
 * 服务Bean工厂
 * @author Wujun
 *
 */
public class ServiceBeanFactory {
	
	private static EnumMap<LockType,Class<?>> serviceMap=new EnumMap<>(LockType.class);
	
	static {
		serviceMap.put(LockType.REENTRANT, ReentrantLockServiceImpl.class);
		serviceMap.put(LockType.FAIR, FairLockServiceImpl.class);
		serviceMap.put(LockType.MULTI, MultiLockServiceImpl.class);
		serviceMap.put(LockType.READ, ReadLockServiceImpl.class);
		serviceMap.put(LockType.RED, RedLockServiceImpl.class);
		serviceMap.put(LockType.WRITE, WriteLockServiceImpl.class);
	}

	/**
	 * 根据锁类型获取相应的服务处理类
	 * @param lockType
	 * @return
	 * @throws ServiceNotFoundException
	 */
	public LockService getService(LockType lockType) throws ServiceNotFoundException {
		LockService lockService = (LockService) SpringUtil.getBean(serviceMap.get(lockType));
		if(lockService==null) {
			throw new ServiceNotFoundException();
		}
		return lockService;
	}
	
}
