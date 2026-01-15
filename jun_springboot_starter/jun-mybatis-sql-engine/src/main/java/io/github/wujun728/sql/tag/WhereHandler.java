package io.github.wujun728.sql.tag;

import io.github.wujun728.sql.node.MixedSqlNode;
import io.github.wujun728.sql.node.SqlNode;
import io.github.wujun728.sql.node.WhereSqlNode;
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
