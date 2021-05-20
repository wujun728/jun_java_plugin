package com.ketayao.learn.jsoup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GrabOschinaCode {
	private static String URL = "http://www.oschina.net/code/tag/";
	private String tempDir = "d://temp/";

	public static void main(String[] args) {
		GrabOschinaCode grabOschinaCode = new GrabOschinaCode();
		grabOschinaCode.grabCodeByTag("jsoup");
		//grabOschinaCode.downloadJavaCode("http://www.oschina.net/code/snippet_572254_15878");
		
		//String tempString = "view.Util";
		//System.out.println(tempString.replaceAll("\\.", "/"));
	}
	
	public void grabCodeByTag(String tagName) {
		String tagUrl = URL + tagName + "?p=1";
		
		try {
			Document document = Jsoup.connect(tagUrl).userAgent("Mozilla").get(); //处理首页

			List<String> titles = new ArrayList<String>();
			
			Elements titleLinkElements = document.select("h3.code_title > a");
			for (Element element : titleLinkElements) {
				titles.add(element.attr("href"));
			}
			
			// 得到最大页数
			//Element maxPageElement = document.select("ul[class=pager] > li:last-child + li").first();
			int size = document.select("ul.pager li").size();
			Element maxPageElement = document.select("ul.pager li").get(size - 2);
			int maxPage = NumberUtils.toInt(maxPageElement.select("a").text());
			System.out.println("maxPage=" + maxPage);
			for (int i = 2; i < maxPage; i++) {
				String pageUrl = URL + tagName + "?p=" + i;
				Document doc = Jsoup.connect(pageUrl).userAgent("Mozilla").get(); //处理首页
				Elements tlElements = doc.select("h3.code_title > a");
				for (Element element : tlElements) {
					titles.add(element.attr("href"));
				}
			}
			
			for (String t : titles) {
				downloadJavaCode(t);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void downloadJavaCode(String url) {
		try {
			Document document = Jsoup.connect(url).userAgent("Mozilla").get();
			
			Elements javaCodeElements = document.select("pre[class*=java]");
			for (Element element : javaCodeElements) {
				downloadFile(element.text());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public void downloadFile(String codeText) {
		String dir = tempDir;
		// 分析包名
		int packageIndex = codeText.indexOf("package ");
		if (packageIndex > -1) {
			String temp = StringUtils.substringBetween(codeText, "package ", ";");
			dir = dir + temp.replaceAll("\\.", "/") + "/";
		}
		
		// 分析class名
		int classIndex = codeText.indexOf("public class ");
		if (classIndex > -1) {
			dir = dir + StringUtils.substringBetween(codeText, "public class ", " "); 
			if (dir.indexOf("{") > -1) {
				dir = StringUtils.substringBefore(dir, "{");
			}
			dir +=".java";
		} else {
			return;//忽略其他情况
		}
		
		File fileDest = new File(dir);
		if (!fileDest.getParentFile().exists()) {
			fileDest.getParentFile().mkdirs();
		}
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fileDest);
			IOUtils.write(codeText, fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(fos);
		}
	}
}
