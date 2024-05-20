package com.jun.plugin.sql.tag;

import com.jun.plugin.sql.node.IfSqlNode;
import com.jun.plugin.sql.node.MixedSqlNode;
import org.dom4j.Element;

import com.jun.plugin.sql.node.SqlNode;

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
