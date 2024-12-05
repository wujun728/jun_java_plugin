package io.github.wujun728.sql.tag;

import io.github.wujun728.sql.node.MixedSqlNode;
import io.github.wujun728.sql.node.SetSqlNode;
import io.github.wujun728.sql.node.SqlNode;
import org.dom4j.Element;

import java.util.List;


public class SetHandler implements TagHandler{

    @Override
    public void handle(Element element, List<SqlNode> targetContents) {
        List<SqlNode> contents = XmlParser.parseElement(element);

        SetSqlNode node = new SetSqlNode(new MixedSqlNode(contents));
        targetContents.add(node);
    }
}
