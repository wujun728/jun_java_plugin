package com.jun.plugin.freemark.code.create;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jun.plugin.freemark.code.entity.Columns;
import com.jun.plugin.freemark.code.entity.Table;
import com.jun.plugin.freemark.code.sql.SelectTableSql;

/**
 * 编写一个代码生成的小程序
 * 
 * @author shichenyang
 * 
 */
public class CreateJava {

	public static Map<String, String> map=new HashMap<String, String>();
	
	static{
		map.put("VARCHAR", "String");
		map.put("DECIMAL", "Double");
		map.put("DATE", "Date");
	}
	public static void main(String[] args) {
		SelectTableSql updateSql = new SelectTableSql();
		List<Table> tables=null;
		try {
			tables = updateSql.getSchema();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		
		for (Table table : tables) {
			StringBuffer java = new StringBuffer();
			String tableName=table.getTableName().substring(0, 1).toUpperCase()+table.getTableName().substring(1);
			java.append("package com.yang.model;\n\n");
			java.append("import java.util.Date;\n\n");
			java.append("public class " + tableName + "{\n");
			// 循环一下列信息
			for (Columns item : table.getList()) {
				java.append("	//" + item.getREMARKS()+"\n");// 加上注释
				// COLUMN_TYPE字段类型，COLUMN_NAME字段名称
				java.append("	private " + map.get(item.getColumnType()) + " "
						+ item.getColumnName() + ";\n");

			}
			java.append("\n");
			// 循环一下列信息
			for (Columns item : table.getList()) {
				String name = item.getColumnName();
				// 拼接一下get方法
				java.append("	public " + map.get(item.getColumnType()) + " get"
						+ name.substring(0, 1).toUpperCase()
						+ name.substring(1) + "(){\n");
				java.append("		return " + name + ";\n");
				java.append("	}\n");

				// 拼接一下set方法
				java.append("	public void" + " set"
						+ name.substring(0, 1).toUpperCase()
						+ name.substring(1) + "(" + map.get(item.getColumnType()) + " "
						+ name + "){\n");
				java.append("		this. " + name + "=" + name + ";\n");
				java.append("	}\n");
			}

			java.append("}\n");

			BufferedWriter writer = null;
			try {
				writer = new BufferedWriter(new FileWriter("D:\\"
						+ tableName + ".java"));
				writer.write(java.toString());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
