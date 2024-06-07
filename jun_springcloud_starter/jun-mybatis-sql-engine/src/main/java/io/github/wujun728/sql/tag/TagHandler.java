package io.github.wujun728.sql.tag;

import io.github.wujun728.sql.node.SqlNode;
import org.dom4j.Element;

import java.util.List;

public interface TagHandler {

    void handle(Element element, List<SqlNode> contents);
}
