package tqlin;

import org.apache.commons.digester3.Digester;
import org.xml.sax.SAXException;
import tqlin.entity.Bar;
import tqlin.entity.Foo;

import java.io.IOException;
import java.util.Iterator;

/**
 * 简单示例
 */
public class ExampleMain {
    public static void main(String[] args) {
        Digester digester = new Digester();
        digester.setValidating(false);
        digester.addObjectCreate("foo", "tqlin.entity.Foo");
        digester.addSetProperties("foo");

        digester.addObjectCreate("foo/bar", Bar.class);
        digester.addSetProperties("foo/bar");

        digester.addSetNext("foo/bar", "addBar", "tqlin.entity.Bar");
        Foo foo = null;
        try {
            foo = digester.parse(ExampleMain.class.getClassLoader().getResourceAsStream("example.xml"));
        } catch (IOException e) {
            System.out.println("IO访问异常");
        } catch (SAXException e) {
            System.out.println("XML解析异常");
        }
        if (foo != null) {
            System.out.println(foo.getName());
            Iterator fooIterator = foo.getBars();
            while (fooIterator.hasNext()) {
                Bar bar = (Bar) fooIterator.next();
                System.out.println("id:" + bar.getId() + "," + "title:" + bar.getTitle());
            }
        }
    }
}
