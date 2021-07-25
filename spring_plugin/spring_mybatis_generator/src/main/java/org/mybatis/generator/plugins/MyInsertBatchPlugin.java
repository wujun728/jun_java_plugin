package org.mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * 生成批量新增接口方法插件
 */
public class MyInsertBatchPlugin extends PluginAdapter {


    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }


    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setName("insertBatchSelective");
        method.addParameter(new Parameter(new FullyQualifiedJavaType("List<"+introspectedTable.getBaseRecordType()+">"), "records"));
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        interfaze.addMethod(method);
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {

        XmlElement valueTrimElement = new XmlElement("trim");
        valueTrimElement.addAttribute(new Attribute("prefix", " ("));
        valueTrimElement.addAttribute(new Attribute("suffix", ")"));
        valueTrimElement.addAttribute(new Attribute("suffixOverrides", ","));

        XmlElement insertTrimElement = new XmlElement("trim");
        insertTrimElement.addAttribute(new Attribute("prefix", "("));
        insertTrimElement.addAttribute(new Attribute("suffix", ")"));
        insertTrimElement.addAttribute(new Attribute("suffixOverrides", ","));
        List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
        for (IntrospectedColumn introspectedColumn : columns) {
            String columnName = introspectedColumn.getActualColumnName();
            XmlElement iftest=new XmlElement("if");
            iftest.addAttribute(new Attribute("test","list[0]."+introspectedColumn.getJavaProperty()+"!=null"));
            iftest.addElement(new TextElement(columnName+","));
            insertTrimElement.addElement(iftest);
            XmlElement trimiftest=new XmlElement("if");
            trimiftest.addAttribute(new Attribute("test","item."+introspectedColumn.getJavaProperty()+"!=null"));
            trimiftest.addElement(new TextElement("#{item." + introspectedColumn.getJavaProperty() + ",jdbcType=" + introspectedColumn.getJdbcTypeName() + "},"));
            valueTrimElement.addElement(trimiftest);
        }

        XmlElement foreachElement = new XmlElement("foreach");
        foreachElement.addAttribute(new Attribute("collection", "list"));
        foreachElement.addAttribute(new Attribute("index", "index"));
        foreachElement.addAttribute(new Attribute("item", "item"));
        foreachElement.addAttribute(new Attribute("separator", ","));
        foreachElement.addElement(valueTrimElement);

        XmlElement insertBatchElement = new XmlElement("insert");
        context.getCommentGenerator().addComment(insertBatchElement);
        insertBatchElement.addAttribute(new Attribute("id", "insertBatchSelective"));
        insertBatchElement.addAttribute(new Attribute("parameterType", "java.util.List"));

        insertBatchElement.addElement(new TextElement("insert into " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        insertBatchElement.addElement(insertTrimElement);
        insertBatchElement.addElement(new TextElement(" values "));
        insertBatchElement.addElement(foreachElement);

        document.getRootElement().addElement(insertBatchElement);
        return true;
    }
}
