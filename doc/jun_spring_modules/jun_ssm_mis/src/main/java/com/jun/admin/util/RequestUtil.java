
package com.jun.admin.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

public class RequestUtil
{

	public static final String URL_FORM_ENCODED = "application/x-www-form-urlencoded";
	public static final String PUT = "PUT";
	public static final String POST = "POST";

	public RequestUtil()
	{
	}

	public static Map getParams(HttpServletRequest request)
	{
		Map params = new HashMap();
		String queryString = request.getQueryString();
		if (queryString != null && queryString.length() > 0)
		{
			String pairs[] = Pattern.compile("&").split(queryString);
			for (int i = 0; i < pairs.length; i++)
			{
				String p = pairs[i];
				int idx = p.indexOf('=');
				params.put(p.substring(0, idx), URLDecoder.decode(p.substring(idx + 1)));
			}

		}
		return params;
	}

	@SuppressWarnings("unchecked")
	public static Map getAllParameters(HttpServletRequest request)
	{
		Map bufferMap = Collections.synchronizedMap(new HashMap());
		try
		{
			for (Enumeration em = request.getParameterNames(); em.hasMoreElements();)
			{
				String name = (String)(String)em.nextElement();
				String values[] = request.getParameterValues(name);
				String temp[] = new String[values.length];
				if (values.length > 1)
				{
					for (int i = 0; i < values.length; i++)
						temp[i] = values[i];

					bufferMap.put(name, temp);
				} else
				{
					bufferMap.put(name, values[0]);
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return bufferMap;
	}

	public static String CharSetConvert(String s, String charSetName, String defaultCharSetName)
	{
		String newString = null;
		try
		{
			newString = new String(s.getBytes(charSetName), defaultCharSetName);
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException nulle)
		{
			nulle.printStackTrace();
		}
		return newString;
	}
}
