package com.techsoft;

import java.io.File;

import com.techsoft.Interceptor.InterceptorManager;
import com.techsoft.container.DataServer;
import com.techsoft.plugins.PluginManager;

public class ConsolePlugin implements Plugin {
	private File pluginDirectory;
	@Override
	public void initializePlugin(PluginManager manager, File pluginDirectory) {
		InterceptorManager.getInstance().addInterceptor(new SqlIdProcessImpl());
	}
	@Override
	public void destroyPlugin() {
		DataServer.getInstance().removePluginModule(pluginDirectory.getName());
	}

}
