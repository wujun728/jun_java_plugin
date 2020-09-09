
# JsoupX
JsoupX 是为链试使用Jsoup 而封装的小工具，
目的是为快捷的过虑出需要的节点，方便编写数据抓取代码等。

##依赖
	jsoup
	junit

##Example: > 更多示例请查看 com.sarq.test.JsoupXTest.java

	// DocumentX doc = JsoupX.parse(html);
	
	String url = "http://www.soku.com/v?keyword=%E4%BB%81%E6%98%BE%E7%8E%8B%E5%90%8E%E7%9A%84%E7%94%B7%E4%BA%BA";
	
	DocumentX doc = JsoupX.connect(url).get();
	
	Elements els = doc.byTag("div").byAttr("class", "source source_one").currentEles();
	
	// System.out.println(els.html());
	
	for (Element el : els) {
	  System.out.println(el.html());
	}
