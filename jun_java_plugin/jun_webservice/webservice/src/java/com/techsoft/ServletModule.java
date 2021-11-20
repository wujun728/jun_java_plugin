package com.techsoft;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;

public interface ServletModule {

	public static String JSONP = "callback";

	public String getName();

	public void process(Map<String, Object> in, boolean isMultiPart,
			InputStream inputs, OutputStream outs, List<FileItem> list,
			Map<String, Object> result, DataConverter dataConverter)
			throws TechException;
}
