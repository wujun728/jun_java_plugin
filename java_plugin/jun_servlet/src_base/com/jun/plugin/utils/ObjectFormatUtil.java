package com.jun.plugin.utils;


public class ObjectFormatUtil {
	public static boolean isNotNull(String param)
	{
		boolean ret = true;
		if(null==param)
			ret = false;
		else if(param.trim().length()==0)
		{
			ret = false;
		}
		return ret;
	}
	public static boolean isNotNull(Integer param)
	{
		boolean ret = true;
		if(null==param)
			ret = false;
		else if(param==0)
		{
			ret = false;
		}
		return ret;
	}
	public static boolean isNotNull(Long param)
	{
		boolean ret = true;
		if(null==param)
			ret = false;
		else if(param==0)
		{
			ret = false;
		}
		return ret;
	}
	/**
	 * @description 去掉末尾的0
	 * @param strDate
	 * @return
	 */
	public static String formatDateStr(String strDate)
	{
		String ret = strDate;
		if(strDate!=null)
		{
			ret = strDate.replaceAll(" 00:00:00.0", "");
		}
		return ret;
	}

}
