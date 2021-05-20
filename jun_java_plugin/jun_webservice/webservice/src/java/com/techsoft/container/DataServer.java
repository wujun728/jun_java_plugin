package com.techsoft.container;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.techsoft.servlet.ConfigProperties;
import com.techsoft.ConnectionPool;
import com.techsoft.ConnectionPoolException;
import com.techsoft.ServletModule;
import com.techsoft.TechException;
import com.techsoft.modules.LoginModule;
import com.techsoft.modules.LogoutModule;
import com.techsoft.modules.QueryMetaModule;
import com.techsoft.modules.QueryModule;
import com.techsoft.modules.QueryParamModule;
import com.techsoft.modules.SaveModule;
import com.techsoft.plugins.PluginManager;
import com.techsoft.pool.ConnectionPoolFactory;

public class DataServer {
	private static final Logger Log = LoggerFactory.getLogger(DataServer.class);
	private static DataServer instance;

	private PluginManager pluginsManager;
	private File webHome;
	private Map<String, ServletModule> coreHandlers;
	private Map<String, Map<String, ServletModule>> pluginHandlers;
	private Map<String, Map<String, ServletModule>> newpluginHandlers;
	private ConfigProperties properties;
	private ConnectionPool pool;
	private boolean debug = false;
	private boolean started = false;

	public boolean isStarted() {
		return started;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public boolean isDebug() {
		return debug;
	}

	public ConnectionPool getPool() {
		return pool;
	}

	public ConfigProperties getProperties() {
		return properties;
	}

	public PluginManager getPluginsManager() {
		return pluginsManager;
	}

	public Map<String, ServletModule> getCoreHandlers() {
		return coreHandlers;
	}

	public void addModule(ServletModule module) {
		coreHandlers.put(module.getName().toLowerCase(), module);
	}

	private boolean isExistpluginHandlers(ServletModule module) {
		boolean result = false;
		Map<String, ServletModule> modules = null;
		ServletModule iterModule = null;
		Iterator<Map<String, ServletModule>> itermodules = pluginHandlers.values()
				.iterator();
		while (itermodules.hasNext()) {
			modules = itermodules.next();
			Iterator<ServletModule> iter = modules.values().iterator();
			while (iter.hasNext()) {
				iterModule = iter.next();
				if (iterModule.getName().equalsIgnoreCase(module.getName())) {
					result = true;
					break;
				}
			}
		}

		return result;
	}

	private boolean isExistnewPluginHandlers(ServletModule module) {
		boolean result = false;
		Map<String, ServletModule> modules = null;
		ServletModule iterModule = null;
		Iterator<Map<String, ServletModule>> itermodules = newpluginHandlers.values()
				.iterator();
		while (itermodules.hasNext()) {
			modules = itermodules.next();
			Iterator<ServletModule> iter = modules.values().iterator();
			while (iter.hasNext()) {
				iterModule = iter.next();
				if (iterModule.getName().equalsIgnoreCase(module.getName())) {
					result = true;
					break;
				}
			}
		}

		return result;
	}

	public void addPluginModule(String pluginName, ServletModule module)
			throws TechException {
		if (isExistpluginHandlers(module)) {
			if (!isExistnewPluginHandlers(module)) {
				Map<String, ServletModule> modules = null;
				if (newpluginHandlers.get(pluginName) == null) {
					modules = new HashMap<String, ServletModule>();
				} else {
					modules = newpluginHandlers.get(pluginName);
				}
				modules.put(module.getName(), module);
				newpluginHandlers.put(pluginName, modules);
			} else {
				Log.error(module.getName() + "  此模块的的方法名称已经存在， 请修改模块名称!");
				throw new TechException(module.getName()
						+ "  此模块的的方法名称已经存在， 请修改模块名称!");
			}
		} else {
			Map<String, ServletModule> modules = null;
			if (pluginHandlers.get(pluginName) == null) {
				modules = new HashMap<String, ServletModule>();
			} else {
				modules = pluginHandlers.get(pluginName);
			}
			modules.put(module.getName(), module);
			pluginHandlers.put(pluginName, modules);
		}
	}

	public ServletModule getPluginModuleByMethod(String methodName) {
		ServletModule result = null;
		ServletModule module = null;
		Iterator<Map<String, ServletModule>> itermap = newpluginHandlers.values()
				.iterator();
		Map<String, ServletModule> map = null;
		Iterator<ServletModule> iter = null;
		while (itermap.hasNext()) {
			map = itermap.next();
			iter = map.values().iterator();
			while (iter.hasNext()) {
				module = iter.next();
				if (module.getName().equalsIgnoreCase(methodName)) {
					result = module;
					break;
				}
			}
		}

		if (result == null) {
			itermap = pluginHandlers.values().iterator();
			while (itermap.hasNext()) {
				map = itermap.next();
				iter = map.values().iterator();
				while (iter.hasNext()) {
					module = iter.next();
					if (module.getName().equalsIgnoreCase(methodName)) {
						result = module;
						break;
					}
				}
			}
		}

		return result;
	}

	public void removePluginModule(String pluginName) {
		if (pluginHandlers.get(pluginName) != null) {
			pluginHandlers.remove(pluginName);
		} else {
			newpluginHandlers.remove(pluginName);
		}
	}

	public DataServer(File webHome) {
		if (instance != null) {
			Log.error("A server is already running");
			throw new IllegalStateException("A server is already running");
		}
		this.webHome = webHome;
		coreHandlers = new ConcurrentHashMap<String, ServletModule>();
		pluginHandlers = new ConcurrentHashMap<String, Map<String, ServletModule>>();
		newpluginHandlers = new ConcurrentHashMap<String, Map<String, ServletModule>>();
		properties = new ConfigProperties(webHome);

		instance = this;
	}

	public static DataServer getInstance() {
		return instance;
	}

	public void start() {
		File pluginDir = null;
		File logdir = null;
		debug = Boolean.parseBoolean(properties.getisDebug());
		Integer maxpool = 5;
		Integer minpool = 1;
		Integer initpool = 1;
		try {
			maxpool = Integer.valueOf(properties.getMaxPool());
		} catch (Exception e) {
			maxpool = 5;
		}
		try {
			minpool = Integer.valueOf(properties.getMinPool());
		} catch (Exception e) {
			minpool = 1;
		}
		try {
			initpool = Integer.valueOf(properties.getDefaultPool());
		} catch (Exception e) {
			initpool = 1;
		}

		try {
			pool = ConnectionPoolFactory.getInstance().getConnectionPool();

			pool.setDriverName(properties.getDriver());
			pool.setJdbcURL(properties.getURL());
			pool.setUserName(properties.getUser());
			pool.setPassword(properties.getPassword());
			pool.setMaxPool(maxpool);
			pool.setMinPool(minpool);
			pool.setInitPool(initpool);
			pool.setDatabaseType(properties.getDbtype());
			try {
				pool.start();

				this.addModule(QueryModule.getInstance());
				this.addModule(SaveModule.getInstance());
				this.addModule(LoginModule.getInstance());
				this.addModule(LogoutModule.getInstance());
				this.addModule(QueryParamModule.getInstance());
				this.addModule(QueryMetaModule.getInstance());
				pluginDir = new File(webHome, "plugins");
				if (!pluginDir.exists()) {
					pluginDir.mkdirs();
				}

				logdir = new File(webHome, "logs");
				if (!logdir.exists()) {
					logdir.mkdirs();
				}
				// 必须等连接池启动完后才启动插件，因为插件有可能使用连接池
				pluginsManager = new PluginManager(pluginDir);
				pluginsManager.setIsDebug(debug);
				Log.info("pluginsManager debug is "
						+ pluginsManager.getIsDebug());
				pluginsManager.start();

				started = true;
			} catch (ConnectionPoolException e) {
				this.shutdown();
				Log.error(e.getMessage(), e);
			}
		} catch (ConnectionPoolException e) {
			this.shutdown();
			Log.error(e.getMessage(), e);
		}
	}

	public void shutdown() {
		started = false;
		if (pluginsManager != null) {
			pluginsManager.shutdown();
			pluginsManager = null;
		}

		if (pool != null) {
			try {
				pool.shutdown();
			} catch (Exception e) {
				// 这里可能会有内存问题
				try {
					pool.shutdown();
				} catch (Exception e1) {
					//
				}
			}
			pool = null;
		}
		coreHandlers.clear();
		pluginHandlers.clear();
		newpluginHandlers.clear();
	}
}
