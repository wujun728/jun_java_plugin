
package com.jun.admin.util;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ResultSetUtil
{

	public ResultSetUtil()
	{
	}

	public static List extractData(ResultSet rs)
		throws SQLException
	{
		List results = new LinkedList();
		int rowNum = 0;
		for (; rs.next(); results.add(mapRow(rs, rowNum++)));
		return results;
	}

	public static List extractData(ResultSet rs, int rowsExpected)
		throws SQLException
	{
		List results = ((List) (rowsExpected <= 0 ? ((List) (new LinkedList())) : ((List) (new ArrayList(rowsExpected)))));
		int rowNum = 0;
		for (; rs.next(); results.add(mapRow(rs, rowNum++)));
		return results;
	}

	public static Object mapRow(ResultSet rs, int rowNum)
		throws SQLException
	{
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		Map mapOfColValues = new HashMap(columnCount);
		for (int i = 1; i <= columnCount; i++)
		{
			String key = getColumnKey(rsmd.getColumnName(i));
			Object obj = getColumnValue(rs, i);
			mapOfColValues.put(key, obj);
		}

		return mapOfColValues;
	}

	private static String getColumnKey(String columnName)
	{
		return columnName;
	}

	private static Object getColumnValue(ResultSet rs, int index)
		throws SQLException
	{
		return getResultSetValue(rs, index);
	}

	private static Object getResultSetValue(ResultSet rs, int index)
		throws SQLException
	{
		Object obj = rs.getObject(index);
		if (obj instanceof Blob)
			obj = rs.getBytes(index);
		else
		if (obj instanceof Clob)
			obj = rs.getString(index);
		else
		if (obj != null && obj.getClass().getName().startsWith("oracle.sql.TIMESTAMP"))
			obj = rs.getTimestamp(index);
		else
		if (obj != null && obj.getClass().getName().startsWith("oracle.sql.DATE"))
		{
			String metaDataClassName = rs.getMetaData().getColumnClassName(index);
			if ("java.sql.Timestamp".equals(metaDataClassName) || "oracle.sql.TIMESTAMP".equals(metaDataClassName))
				obj = rs.getTimestamp(index);
			else
				obj = rs.getDate(index);
		} else
		if (obj != null && (obj instanceof Date) && "java.sql.Timestamp".equals(rs.getMetaData().getColumnClassName(index)))
			obj = rs.getTimestamp(index);
		return obj;
	}
}
