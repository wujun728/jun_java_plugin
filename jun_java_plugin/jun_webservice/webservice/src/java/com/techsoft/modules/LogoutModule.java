package com.techsoft.modules;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.techsoft.DataConverter;
import com.techsoft.ServletModule;
import com.techsoft.TechException;

public class LogoutModule implements ServletModule {
	private static final Logger Log = LoggerFactory.getLogger(LogoutModule.class);
	private static final String name = "logout";
	private static LogoutModule instance = null;

	private LogoutModule() {
		Log.info("Log out...");
	}

	public static LogoutModule getInstance() {
		if (instance == null) {
			instance = new LogoutModule();
		}

		return instance;
	}

	@Override
	public String getName() {
		return LogoutModule.name;
	}

	public void removeSession(Map<String, Object> in, InputStream input, Map<String, Object> result) {

	}

	@Override
	public void process(Map<String, Object> in, boolean isMultiPart, InputStream inputs, OutputStream outs,
			List<FileItem> list, Map<String, Object> results, DataConverter dataConverter) throws TechException {
		removeSession(in, inputs, results);
	}

}
