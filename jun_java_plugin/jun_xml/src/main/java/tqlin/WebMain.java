package tqlin;

import org.apache.commons.digester3.Digester;
import org.xml.sax.SAXException;
import tqlin.entity.ServletBean;

import java.io.IOException;

/**
 * web.xml解析示例
 */
public class WebMain {
    public static void main(String[] args) {
        Digester digester = new Digester();
        digester.setValidating(false);
        digester.addObjectCreate("web-app/servlet", "tqlin.entity.ServletBean");
        digester.addCallMethod("web-app/servlet/servlet-name", "setServletName", 0);
        digester.addCallMethod("web-app/servlet/servlet-class", "setServletClass", 0);
        digester.addCallMethod("web-app/servlet/init-param", "addInitParam", 2);
        digester.addCallParam("web-app/servlet/init-param/param-name", 0);
        digester.addCallParam("web-app/servlet/init-param/param-value", 1);
        ServletBean servletBean = null;
        try {
            servletBean = digester.parse(WebMain.class.getClassLoader().getResourceAsStream("web.xml"));
        } catch (IOException e) {
            System.out.println("IO访问异常");
        } catch (SAXException e) {
            System.out.println("XML解析异常");
        }

        if (servletBean != null) {
            System.out.println(servletBean.getServletName());
            System.out.println(servletBean.getServletClass());
            for (String key : servletBean.getInitParams().keySet()) {
                System.out.println(key + ": " + servletBean.getInitParams().get(key));
            }
        }
    }
}
