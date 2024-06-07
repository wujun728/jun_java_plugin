package com.jun.plugin.sql.tag;

import com.jun.plugin.sql.node.MixedSqlNode;
import org.dom4j.Element;

import com.jun.plugin.sql.node.SetSqlNode;
import com.jun.plugin.sql.node.SqlNode;

import java.util.List;


public class SetHandler implements TagHandler{

    @Override
    public void handle(Element element, List<SqlNode> targetContents) {
        List<SqlNode> contents = XmlParser.parseElement(element);

        SetSqlNode node = new SetSqlNode(new MixedSqlNode(contents));
        targetContents.add(node);
    }
}
