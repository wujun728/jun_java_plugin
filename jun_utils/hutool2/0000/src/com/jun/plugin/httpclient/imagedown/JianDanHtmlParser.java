package com.jun.plugin.httpclient.imagedown;

import java.util.ArrayList;
import java.util.List;

public class JianDanHtmlParser implements Runnable {
	private String html;
	private int page;
	public JianDanHtmlParser(String html,int page) {
		this.html = html;
		this.page = page;
	}
	@Override
	public void run() {
		System.out.println("==========ตฺ"+page+"าณ============");
		List<String> list = new ArrayList<String>();
		html = html.substring(html.indexOf("commentlist"));
		String[] images = html.split("li>");
		for (String image : images) {
			String[] ss = image.split("br");
			for (String s : ss) {
				if (s.indexOf("<img src=") > 0) {
					try{
						int i = s.indexOf("<img src=\"") + "<img src=\"".length();
						list.add(s.substring(i, s.indexOf("\"", i + 1)));
					}catch (Exception e) {
						System.out.println(s);
					}
				}
			}
		}
		for(String imageUrl : list){
			if(imageUrl.indexOf("sina")>0){
				new Thread(new JianDanImageCreator(imageUrl,page)).start();
			}
		}
	}
}