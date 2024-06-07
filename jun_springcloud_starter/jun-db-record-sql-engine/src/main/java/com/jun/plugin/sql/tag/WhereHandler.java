package com.jun.plugin.sql.tag;

import com.jun.plugin.sql.node.MixedSqlNode;
import com.jun.plugin.sql.node.SqlNode;
import com.jun.plugin.sql.node.WhereSqlNode;
import org.dom4j.Element;

import java.util.List;


public class WhereHandler implements TagHandler{

    @Override
    public void handle(Element element, List<SqlNode> targetContents) {
        List<SqlNode> contents = XmlParser.parseElement(element);

        WhereSqlNode node = new WhereSqlNode(new MixedSqlNode(contents));
        targetContents.add(node);
    }
}
