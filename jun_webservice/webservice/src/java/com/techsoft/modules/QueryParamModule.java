package com.techsoft.modules;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;

import com.techsoft.DataConverter;
import com.techsoft.ServletModule;
import com.techsoft.TechException;
import com.techsoft.sql.SQLObjectManager;

public class QueryParamModule implements ServletModule {
	private static final String moduleName = "queryparams";
	private static final String sqldefine = "sqldefine";
	private static QueryParamModule instance = null;

	private QueryParamModule() {
	}

	public static QueryParamModule getInstance() {
		if (instance == null) {
			instance = new QueryParamModule();
		}

		return instance;
	}

	@Override
	public String getName() {
		return QueryParamModule.moduleName;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> readParamfromInputStream(Map<String, Object> in,
			InputStream input, DataConverter dataConverter) {
		Map<String, Object> result = null;
		if (input != null) {
			try {
				Object obj = dataConverter.readFromInputStream(input);
				if (obj != null) {
					result = (Map<String, Object>) dataConverter
							.deserializeObject(obj);
				} else {
					result = new HashMap<String, Object>();
				}
			} catch (Exception e) {
				// 输入流中可能不存在内容，在Ajax调用时
				result = new HashMap<String, Object>();
			}

		} else {
			result = new HashMap<String, Object>();
		}
		String key = null;
		Object value = null;
		Iterator<String> iter = in.keySet().iterator();
		while (iter.hasNext()) {
			key = iter.next();
			value = in.get(key);
			value = dataConverter.deserializeObject(value);
			result.put(key, value);
		}
		return result;
	}

	@Override
	public void process(Map<String, Object> in, boolean isMultiPart,
			InputStream inputs, OutputStream outs, List<FileItem> list,
			Map<String, Object> result, DataConverter dataConverter)
			throws TechException {
		Map<String, Object> input = this.readParamfromInputStream(in, inputs,
				dataConverter);

		SQLObjectManager.getInstance().queryParams(
				(String) input.get(QueryParamModule.sqldefine), result);

	}

}
