package io.github.wujun728.sql.tag;

import io.github.wujun728.sql.node.SqlNode;
import org.dom4j.Element;

import io.github.wujun728.sql.node.IfSqlNode;
import io.github.wujun728.sql.node.MixedSqlNode;

import java.util.List;


public class IfHandler implements TagHandler {

    @Override
    public void handle(Element element, List<SqlNode> targetContents) {
        String test = element.attributeValue("test");
        if (test == null) {
            throw new RuntimeException("<if> tag missing test attribute");
        }

        List<SqlNode> contents = XmlParser.parseElement(element);

        IfSqlNode ifSqlNode = new IfSqlNode(test, new MixedSqlNode(contents));
        targetContents.add(ifSqlNode);

    }
}
