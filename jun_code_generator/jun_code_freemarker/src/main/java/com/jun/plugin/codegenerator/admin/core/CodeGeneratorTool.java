package com.jun.plugin.codegenerator.admin.core;


import java.io.IOException;

import com.jun.plugin.codegenerator.admin.core.model.ClassInfo;
import com.jun.plugin.codegenerator.admin.core.util.TableParseUtil;

/**
 * code generate tool
 */
public class CodeGeneratorTool {

	/**
	 * process Table Into ClassInfo
	 *
	 * @param tableSql
	 * @return
	 */
	public static ClassInfo processTableIntoClassInfo(String tableSql) throws IOException {
		return TableParseUtil.processTableIntoClassInfo(tableSql);
	}
	

}