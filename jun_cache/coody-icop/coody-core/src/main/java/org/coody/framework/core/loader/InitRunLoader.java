package org.coody.framework.core.loader;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.coody.framework.core.bean.InitBeanFace;
import org.coody.framework.core.container.BeanContainer;
import org.coody.framework.core.loader.iface.IcopLoader;
import org.coody.framework.core.threadpool.ThreadBlockPool;
import org.coody.framework.core.util.PrintException;
import org.coody.framework.core.util.StringUtil;

/**
 * 切面加载器
 * 
 * @author Coody
 *
 */
public class InitRunLoader implements IcopLoader {

	private static final Logger logger = Logger.getLogger(InitRunLoader.class);

	@Override
	public void doLoader(Set<Class<?>> clazzs) throws Exception {
		List<Runnable> inits = new ArrayList<Runnable>();
		for (Object bean : BeanContainer.getBeans()) {
			if (bean instanceof InitBeanFace) {
				// 初始化运行
				try {
					logger.debug("初始化执行 >>"+bean.getClass().getName());
					InitBeanFace face = (InitBeanFace) bean;
					inits.add(new Runnable() {
						@Override
						public void run() {
							try {
								face.init();
							} catch (Exception e) {
								PrintException.printException(logger, e);
							}
						}
					});
				} catch (Exception e) {
					PrintException.printException(logger, e);
				}
			}
		}
		if (!StringUtil.isNullOrEmpty(inits)) {
			new ThreadBlockPool().execute(inits);
		}
	}

}
