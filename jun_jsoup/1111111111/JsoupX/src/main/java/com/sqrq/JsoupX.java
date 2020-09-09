package com.sqrq;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsoupX {

  public int timeOut = 999;
  private Connection conn;
  private Document doc;
  private static String url = null;

  private JsoupX(Connection conn) {
    this.conn = conn;
  }

  public static JsoupX connect(String url) {
    JsoupX.url = url;
    return new JsoupX(Jsoup.connect(url));
  }

  public JsoupX timeout(int time) {
    timeOut = time;
    return this;
  }

  public com.sqrq.DocumentX get() throws IOException {
    doc = conn.timeout(timeOut).get();
    com.sqrq.DocumentX d = new com.sqrq.DocumentX(doc);
    d.all();
    return d;
  }
  
  public com.sqrq.DocumentX post() throws IOException {
    doc = conn.timeout(timeOut).post();
    com.sqrq.DocumentX d = new com.sqrq.DocumentX(doc);
    d.all();
    return d;
  }

  public static com.sqrq.DocumentX parse(String html) {
    return new com.sqrq.DocumentX(Jsoup.parse(html));
  }
  
  public static com.sqrq.DocumentX parse(Elements els) {
    return new com.sqrq.DocumentX().parse(els);
  }

  public static String root(String url) {
    Pattern p =
        Pattern.compile("[a-zA-z]+://[^\\s'\"]*\\.[a-zA-Z]{2,6}",
            Pattern.CASE_INSENSITIVE);
    Matcher matcher = p.matcher(url);
    if (matcher.find()) {
      String s = matcher.group();
      return s;
    }

    try {
		throw new Exception("没有找到网站根目录！");
	} catch (Exception e) {
		e.printStackTrace();
	}

	return null;
  }

  public static String domain(String url) {
    Pattern p =
        Pattern.compile("^(http|https)://?([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}(/)", Pattern.CASE_INSENSITIVE);
//	    Pattern.compile("[^\\s'\"./:]*.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
    try {
      Matcher matcher = p.matcher(url);
      if (matcher.find()) return matcher.group();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  public static void main(String[] args) {
    String fileName =
        "http://tool.oschina.net//img/google_custom_search_watermark.gif".replaceAll("\\/\\/", "/");
    System.out.println(fileName);
  }


}
