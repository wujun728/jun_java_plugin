package tqlin;

import org.apache.commons.digester3.Digester;
import org.xml.sax.SAXException;
import tqlin.entity.Bar;
import tqlin.entity.Foo;

import java.io.IOException;
import java.util.Iterator;

/**
 * 带命名空间的xml解析示例
 */
public class ExampleNSMain {
    public static void main(String[] args) {

        Digester digester = new Digester();

        digester.setValidating(false);
        digester.setNamespaceAware(true);
        digester.setRuleNamespaceURI("http://www.mycompany.com/MyNamespace");

        digester.addObjectCreate("foo", Foo.class);
        digester.addSetProperties("foo");
        digester.addObjectCreate("foo/bar", Bar.class);
        digester.addSetProperties("foo/bar");

        digester.addSetNext("foo/bar", "addBar", Bar.class.getName());

        Foo foo = null;
        try {
            foo = digester.parse(ExampleNSMain.class.getClassLoader().getResourceAsStream("example_ns.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
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
