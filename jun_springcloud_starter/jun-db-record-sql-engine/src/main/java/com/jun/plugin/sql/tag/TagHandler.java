package com.jun.plugin.sql.tag;

import com.jun.plugin.sql.node.SqlNode;
import org.dom4j.Element;

import java.util.List;

public interface TagHandler {

    void handle(Element element, List<SqlNode> contents);
}
