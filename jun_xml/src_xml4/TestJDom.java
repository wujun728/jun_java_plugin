import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

/**
 * JDom读写xml
 * @author whwang
 */
public class TestJDom {
    public static void main(String[] args) {
       read();
        write();
    }
    
    public static void read() {
        try {
            boolean validate = false;
            SAXBuilder builder = new SAXBuilder(validate);
            InputStream in = TestJDom.class.getClassLoader().getResourceAsStream("university.xml");
            Document doc = builder.build(in);
            // 获取根节点 <university>
            Element root = doc.getRootElement();
            readNode(root, "");
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    public static void readNode(Element root, String prefix) {
        if (root == null) return;
        // 获取属性
        List<Attribute> attrs = root.getAttributes();
        if (attrs != null && attrs.size() > 0) {
            System.err.print(prefix);
            for (Attribute attr : attrs) {
                System.err.print(attr.getValue() + " ");
            }
            System.err.println();
        }
        // 获取他的子节点
        List<Element> childNodes = root.getChildren();
        prefix += "\t";
        for (Element e : childNodes) {
            readNode(e, prefix);
        }
    }
    
    public static void write() {
        boolean validate = false;
        try {
            SAXBuilder builder = new SAXBuilder(validate);
            InputStream in = TestJDom.class.getClassLoader().getResourceAsStream("university.xml");
            Document doc = builder.build(in);
            // 获取根节点 <university>
            Element root = doc.getRootElement();
            // 修改属性
            root.setAttribute("name", "tsu");
            // 删除
            boolean isRemoved = root.removeChildren("college");
            System.err.println(isRemoved);
            // 新增
            Element newCollege = new Element("college");
            newCollege.setAttribute("name", "new_college");
            Element newClass = new Element("class");
            newClass.setAttribute("name", "ccccc");
            newCollege.addContent(newClass);
            root.addContent(newCollege);
            XMLOutputter out = new XMLOutputter();
            File file = new File("src/jdom-modify.xml");
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            out.output(doc, fos);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}