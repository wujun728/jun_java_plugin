package com.ketayao.learn.jsoup;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * <a href="http://my.oschina.net/arthor" target="_blank" rel="nofollow">@author</a>  Bruce Yang
 * 改了一下，单机版了，强烈建议放码不要放一半留一半，真没啥意思~
 */
public class HTMLImageFetcher {
	protected final static String img_path = "/Users/user/Desktop/ImageFetcher/";
	private final static String user_agent = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)";
	protected final static SimpleDateFormat FMT_FN = new SimpleDateFormat("yyyyMM/ddHHmmss_");
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String html = "这张图片很漂亮啊！"
				+ "<img src='http://techcn.com.cn/uploads/201004/12705551313uy0urE3.jpg' alt=''/>"
				+ " ，太帅了！<img src='/img/logo.gif' alt='oschina'/>";
		System.out.println(fetchHTML_Images(html));
	}

	/**
	 * 下载HTML文档中的所有图片
	 * @param html
	 * @return
	 */
	protected static String fetchHTML_Images(String html) {
		if (StringUtils.isBlank(html)) {
			return html;
		}
		HashMap<String, String> img_urls = new HashMap<String, String>();
		Document doc = Jsoup.parse(html);
		Elements imgs = doc.select("img");
		for (int i = 0; i < imgs.size(); i++) {
			Element img = imgs.get(i);
			String src = img.attr("src");
			if (!src.startsWith("/")) {
				try {
					URL imgUrl = new URL(src);
					String imgHost = imgUrl.getHost().toLowerCase();
					if (!imgHost.endsWith(".oschina.net")) {
						String new_src = img_urls.get(src);
						if (new_src == null) {
							new_src = fetchImageViaHttp(imgUrl);
							img_urls.put(src, new_src);
						}
						img.attr("src", new_src);
					}
				} catch (MalformedURLException e) {
					img.remove();
				} catch (Exception e) {
					e.printStackTrace();
					img.remove();
				}
			}
		}
		return doc.body().html();
	}

	private static String fetchImageViaHttp(URL imgUrl) throws IOException {
		String sURL = imgUrl.toString();
		String imgFile = imgUrl.getPath();
		HttpURLConnection conn = (HttpURLConnection) imgUrl.openConnection();
		String uri = null;
		try {
			conn.setAllowUserInteraction(false);
			conn.setDoOutput(true);
			conn.addRequestProperty("Cache-Control", "no-cache");
			conn.addRequestProperty("User-Agent", user_agent);
			conn.addRequestProperty("Referer", sURL.substring(0, sURL.indexOf('/', sURL.indexOf('.')) + 1));
			conn.connect();
			
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return null;
			}
				
			InputStream imgData = conn.getInputStream();
			String ext = FilenameUtils.getExtension(imgFile).toLowerCase();
			if (!isImageFile("aa." + ext)) {
				ext = "jpg";
			}
			uri = FMT_FN.format(new Date()) + RandomStringUtils.randomAlphanumeric(4) + '.' + ext;
			System.err.println(uri);

			File fileDest = new File(img_path + uri);
			if (!fileDest.getParentFile().exists()) {
				fileDest.getParentFile().mkdirs();
			}
			
			FileOutputStream fos = new FileOutputStream(fileDest);
			try {
				IOUtils.copy(imgData, fos);
			} finally {
				IOUtils.closeQuietly(imgData);
				IOUtils.closeQuietly(fos);
			}
		} finally {
			conn.disconnect();
		}
		return img_path + uri;
	}

	/** added by Bruce Yang on 2012.04.16.04.01~ */
	private static boolean isImageFile(String fileName) {
		String[] imgSuffixs = {
			".gif", ".jpg", ".jpeg", ".png", 
			".tiff", ".bmp",
		};
		for(String suffix : imgSuffixs) {
			if(fileName.endsWith(suffix)) {
				return true;
			}
		}
		return false;
	}
}