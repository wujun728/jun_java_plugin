package com.jun.admin.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;



public class JsonUtil
{

	private static String top = "{";
	private static String top_ = "}";

	public JsonUtil()
	{
	}

	public static String getJsonByList(List dList)
	{
		String data = "";
		if (dList != null)
		{
			JSONArray ja = JSONArray.fromObject(dList);
			data = ja.toString();
		}
		return data;
	}

	public static String getObjectJsonData(HashMap params)
	{
		StringBuffer data = new StringBuffer();
		int i = 0;
		if (params != null)
		{
			data.append(top);
			for (Iterator it = params.entrySet().iterator(); it.hasNext();)
			{
				java.util.Map.Entry element = (java.util.Map.Entry)it.next();
				String key = (String)element.getKey();
				List dList = (ArrayList)element.getValue();
				if (i > 0)
					data.append(",");
				data.append((new StringBuilder()).append("\"").append(key).append("\"").append(":").toString());
				data.append(getJsonByList(dList));
				i++;
			}

			data.append(top_);
		}
		return data.toString();
	}

	public static String getExtGridJsonData(List dList)
	{
		StringBuffer data = new StringBuffer();
		if (dList != null)
		{
			data.append((new StringBuilder()).append("{\"totalCount\":").append(dList.size()).append(", \"Body\":").toString());
			data.append(getJsonByList(dList));
			data.append("}");
		}
		return data.toString();
	}

	public static String getGridJsonData(int rowNumber, List dList)
	{
		StringBuffer data = new StringBuffer();
		if (dList != null)
		{
			data.append((new StringBuilder()).append("{\"totalCount\":").append(rowNumber).append(", \"Body\":").toString());
			data.append(getJsonByList(dList));
			data.append("}");
		}
		return data.toString();
	}

	public static String getDefineJsonData(String firstParam, String listParam, String firstValue, List dList)
	{
		StringBuffer data = new StringBuffer();
		if (dList != null)
		{
			data.append((new StringBuilder()).append("{\"").append(firstParam).append("\":").append(firstValue).append(", \"").append(listParam).append("\":").toString());
			data.append(getJsonByList(dList));
			data.append("}");
		}
		return data.toString();
	}

	public static String getBasetJsonData(List dList)
	{
		StringBuffer data = new StringBuffer();
		if (dList != null)
		{
			JSONArray ja = JSONArray.fromObject(dList);
			data.append(ja.toString());
		}
		return data.toString();
	}

	public static String getEasyUIJsonData(List dList)
	{
		StringBuffer data = new StringBuffer();
		if (dList != null)
			if (dList.size() > 0)
			{
				String total = StringUtil.toString(((Map)dList.get(0)).get("IRECCOUNT"));
				data.append((new StringBuilder()).append("{\"total\": ").append(total).append(", \"rows\": ").append(getJsonByList(dList)).append("}").toString());
			} else
			{
				data.append("{\"total\": 0, \"rows\": []}");
			}
		System.out.println("json="+data.toString());
		return data.toString();
	}
	
	public static String getEasyUIJsonDataV2(List dList,String total )
	{
		StringBuffer data = new StringBuffer();
		if (dList != null)
			if (dList.size() > 0)
			{
//				String total = StringUtil.toString(((Map)dList.get(0)).get("IRECCOUNT"));
				data.append((new StringBuilder()).append("{\"total\": ").append(total).append(", \"rows\": ").append(getJsonByList(dList)).append("}").toString());
			} else
			{
				data.append("{\"total\": 0, \"rows\": []}");
			}
		System.out.println("total="+total+"json="+data.toString());
		return data.toString();
	}

	public static String getJsonDataByObject(Object obj)
	{
		StringBuffer data = new StringBuffer();
		if (obj != null)
			data.append(JSONArray.fromObject(obj).toString());
		return data.toString();
	}

}
