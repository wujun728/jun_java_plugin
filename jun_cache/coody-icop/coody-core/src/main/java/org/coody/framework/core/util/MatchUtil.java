package org.coody.framework.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchUtil {
	
	
	public static Boolean isMatcher(String val, String matcher) {
		Pattern p = Pattern.compile(matcher);
		Matcher m = p.matcher(val);
		return m.matches();
	}

	

	public static Boolean isParaMatch(String val,String mateher){
		try {
			if(StringUtil.isNullOrEmpty(val)){
				return false;
			}
			List<String> paraNames=getParams(mateher);
			mateher=parseMatchContext(mateher, paraNames);
			return isMatcher(val, mateher);
		} catch (Exception e) {
			
			return false;
		}
	}	
	
	public static String parseMatchContext(String matchContext,List<String> paraNames){
		String exportPat="(.*)";
		for(String para:paraNames){
			matchContext=matchContext.replace("${"+para+"}", exportPat);
		}
		return matchContext;
	}
	
	public static List<String> matchResults(String context,String matchContext){
		String exportPat="(.*)";
		String [] pattenTrunk=matchContext.split("\\(\\.\\*\\)");
		List<String> results=new ArrayList<String>();
		for(int i=0;i<pattenTrunk.length;i++){
			String patten=pattenTrunk[i]+exportPat;
			if(i<pattenTrunk.length-1){
				patten=patten+pattenTrunk[i+1];
			}
			String value=matchExportFirst(context, patten);
			results.add(value);
		}
		return results;
	}
	
	public static Map<String, String> matchParamMap(String context,String matchContext){
		List<String> paraNames=getParams(matchContext);
		matchContext=parseMatchContext(matchContext, paraNames);
		List<String> results=matchResults(context, matchContext);
		HashMap<String,String> map=new HashMap<String, String>();
		for(int i=0;i<paraNames.size();i++){
			try {
				if(map.containsKey(paraNames.get(i))){
					map.put(paraNames.get(i), map.get(paraNames.get(i))+","+results.get(i));
					continue;
				}
				map.put(paraNames.get(i), results.get(i));
			} catch (Exception e) {
			}
		}
		return map;
	}
	
	public static List<String> getParams(String context){
		String patten="\\$\\{([A-Za-z0-9_]+)\\}";
		return matchExport(context, patten);
	}
	
	
	public static String matchExportFirst(String context,String patten){
		List<String> results=matchExport(context, patten);
		if(StringUtil.isNullOrEmpty(results)){
			return null;
		}
		return results.get(0);
	}
	public static List<String> matchExport(String context,String patten){
		try {
			Integer index = 0;
			Pattern pattern = Pattern.compile(patten, Pattern.DOTALL);
			Matcher matcher = pattern.matcher(context);
			List<String> results=new ArrayList<String>();
			while (matcher.find(index)) {
				String tmp = matcher.group(1);
				index = matcher.end();
				if (StringUtil.isNullOrEmpty(tmp)) {
					continue;
				}
				results.add(tmp);
			}
			return results;
		} catch (Exception e) {
			return null;
		}
	}
	
	static String a="abcdefghijklmnopqrsvaaaaaaaaaaa\r\n\rdjskfhj\t...?df[pigodfpgk()";
	
	static String b="${para0}bc${para1}efg${para2}ijkl${para3}nop${para4}rs${para1}";
	
	
}
