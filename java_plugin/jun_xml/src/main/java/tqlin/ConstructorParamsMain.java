package tqlin;

import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.ObjectCreateRule;
import org.xml.sax.SAXException;
import tqlin.entity.MyBean;

import java.io.IOException;

/**
 * 带参构造方法使用示例
 */
public class ConstructorParamsMain {
    public static void main(String[] args) throws IOException, SAXException {

        ObjectCreateRule createRule = new ObjectCreateRule(MyBean.class);
        createRule.setConstructorArgumentTypes(Double.class, Boolean.class);

        Digester digester = new Digester();
        digester.addRule("root/bean", createRule);
        digester.addCallParam("root/bean", 1, "super");
        digester.addCallParam("root/bean/rate", 0);

        MyBean myBean = digester.parse(ConstructorParamsMain.class.getClassLoader().getResourceAsStream("constructor-params.xml"));

        System.out.println(myBean.getRate());
        System.out.println(myBean.isSuper_());
    }
}