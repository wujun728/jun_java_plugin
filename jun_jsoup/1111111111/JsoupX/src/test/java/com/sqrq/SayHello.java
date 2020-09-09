package sqrq;

import com.sqrq.DocumentX;
import com.sqrq.JsoupX;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;

public class SayHello {

  String url = "http://www.soku.com/v?keyword=%E4%BB%81%E6%98%BE%E7%8E%8B%E5%90%8E%E7%9A%84%E7%94%B7%E4%BA%BA";
  
  @Test
  public void helloWord() throws IOException {
    DocumentX doc = JsoupX.connect(url).get();
    
    Elements els = doc.byTag("div").byAttr("class", "source source_one").currentEles();

    // System.out.println(els.html());
    for (Element el : els) {
      System.out.println(el.html());
    }
    System.out.println("very glad to see you , sir !");
  }
  
  @Test
  public void helloWord2() throws Exception {
    String url = "http://hanju.8684.com/hjsszn";
    DocumentX d = JsoupX.connect(url).get();
    
    Elements els = d.byTag("table").byAttr("style", "width: 631px;").subEle(0).byTag("tr").currentEles();
    for (Element el : els) {
      Elements tds = el.getElementsByTag("td");
      System.out.println("====================");
      for (Element td : tds) {
        System.out.println(td.text());
      }
    }
  }
  
  @Test
  public void createDocmentX() throws IOException {
    String html = Jsoup.connect(url).get().html();
    
    // 1
    DocumentX doc = JsoupX.parse(html);

    // 2
    Elements els = doc.all();
    doc = JsoupX.parse(els);

    // 3
    doc = JsoupX.connect(url).get();
  }

  @Test
  public void filter() throws Exception {
    DocumentX doc = JsoupX.connect(url).get();

    Elements divs = doc.byTag("div").currentEles();
    for (Element el : divs)
      System.out.println(el.attr("class"));
  }
  @Test
  public void filters() throws Exception {
    DocumentX doc = JsoupX.connect(url).get();
    
    Elements items = doc.byAttr("class", "item").currentEles();
    int count = 0;
    for (Element el : items) {
      System.out.println(">>>>>>>>>>   " + ++count + "   <<<<<<<<");

      DocumentX dx = JsoupX.parse(el.getElementsByTag("h1"));
      String href = dx.subEle().byTag("a").get(0).attr("href");

      String cname = el.getElementsByTag("h1").text();
      String zhuyan = el.getElementsByAttributeValue("class", "host").text();
      String area = el.getElementsByAttributeValue("class", "area").text();

      System.out.println(cname);
      System.out.println(href);
      System.out.println(zhuyan);
      System.out.println(area);
    }

  }



}
