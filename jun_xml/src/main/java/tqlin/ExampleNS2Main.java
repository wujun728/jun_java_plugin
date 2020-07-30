package tqlin;

import org.apache.commons.digester3.Digester;
import org.xml.sax.SAXException;
import tqlin.entity.Bar;
import tqlin.entity.Foo;

import java.io.IOException;
import java.util.Iterator;

/**
 * 使用命名空间前缀用于模式匹配示例
 */
public class ExampleNS2Main {
    public static void main(String[] args) {

        Digester digester = new Digester();

        digester.setValidating(false);
        digester.setNamespaceAware(false);

        digester.addObjectCreate("m:foo", Foo.class);
        digester.addSetProperties("m:foo");
        digester.addObjectCreate("m:foo/m:bar", Bar.class);
        digester.addSetProperties("m:foo/m:bar");
        digester.addSetNext("m:foo/m:bar", "addBar", Bar.class.getName());

        digester.addObjectCreate("m:foo/y:bar", Bar.class);
        digester.addSetProperties("m:foo/y:bar");
        digester.addSetNext("m:foo/y:bar", "addBar", Bar.class.getName());

        Foo foo = null;
        try {
            foo = digester
                    .parse(ExampleNS2Main.class.getClassLoader().getResourceAsStream("example_ns.xml"));
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
