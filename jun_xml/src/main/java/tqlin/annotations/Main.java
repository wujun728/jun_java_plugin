/*
 * File Name: Main.java
 * Description:
 * Author: http://www.cnblogs.com/chenpi/
 * Create Date: 2017年6月7日
 */
package tqlin.annotations;

import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.annotations.FromAnnotationsRuleModule;
import org.apache.commons.digester3.binder.DigesterLoader;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * 规则注解示例
 */
public class Main {
    public static void main(String[] args) throws IOException, SAXException {
        DigesterLoader loader = DigesterLoader.newLoader(new FromAnnotationsRuleModule() {
            @Override
            protected void configureRules() {
                bindRulesFrom(Channel.class);
            }

        });

        Digester digester = loader.newDigester();

        Channel channel = digester
                .parse(Main.class.getClassLoader().getResourceAsStream("rss.xml"));

        System.out.println(channel.getTitle());
        System.out.println(channel.getImage().getDescription());

    }

}
