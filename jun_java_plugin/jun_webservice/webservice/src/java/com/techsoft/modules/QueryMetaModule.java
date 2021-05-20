package com.techsoft.modules;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;

import com.techsoft.DataConverter;
import com.techsoft.MetaData;
import com.techsoft.ServletModule;
import com.techsoft.TechException;
import com.techsoft.sql.SQLObjectManager;

public class QueryMetaModule implements ServletModule {

	private static final String moduleName = "querymeta";
	private static final String metasqldefine = "metasqldefine";
	private static final String sql_options = "sql_options";
	private static QueryMetaModule instance = null;

	@Override
	public String getName() {
		return QueryMetaModule.moduleName;
	}

	public static QueryMetaModule getInstance() {
		if (instance == null) {
			instance = new QueryMetaModule();
		}

		return instance;
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
			Map<String, Object> results, DataConverter dataConverter)
			throws TechException {
		try {
			Map<String, Object> input = this.readParamfromInputStream(in,
					inputs, dataConverter);

			String metaquerysql = (String) input
					.get(QueryMetaModule.metasqldefine);
			String sql_options = (String) input
					.get(QueryMetaModule.sql_options);
			Boolean checked = Integer.valueOf(sql_options) / Math.pow(2, 0) % 2 >= 1;
			if (metaquerysql != null) {
				metaquerysql = metaquerysql.toLowerCase().trim();
			}
			List<MetaData> metaDatas = SQLObjectManager.getInstance()
					.queryMetaData(metaquerysql, checked);
			results.put("datas", metaDatas);
		} catch (Exception e) {
			throw new TechException(e.getClass().getName() + " \n"
					+ e.getMessage());
		}
	}

}
