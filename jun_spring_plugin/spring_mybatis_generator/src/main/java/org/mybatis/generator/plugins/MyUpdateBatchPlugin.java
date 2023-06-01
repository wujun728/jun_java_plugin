package org.mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;

import java.util.List;

/**
 * 批量修改插件
 */
public class MyUpdateBatchPlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }


    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.setReturnType(new FullyQualifiedJavaType("int"));
        method.setName("updateBatchByPrimaryKeySelective");
        method.addParameter(new Parameter(new FullyQualifiedJavaType("List<"+introspectedTable.getBaseRecordType()+">"), "records"));
        interfaze.addMethod(method);
        return true;
    }


    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {

        XmlElement updateBatchElement = new XmlElement("update");
        context.getCommentGenerator().addComment(updateBatchElement);
        updateBatchElement.addAttribute(new Attribute("id", "updateBatchByPrimaryKeySelective"));
        updateBatchElement.addAttribute(new Attribute("parameterType", "java.util.List"));

        XmlElement foreach=new XmlElement("foreach");
        foreach.addAttribute(new Attribute("collection","list"));
        foreach.addAttribute(new Attribute("item","item"));
        foreach.addAttribute(new Attribute("index","index"));
        foreach.addAttribute(new Attribute("separator",";"));

        foreach.addElement(new TextElement("update " + introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime()));

        XmlElement valueTrimElement = new XmlElement("set");
        List<IntrospectedColumn> columns = ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getNonPrimaryKeyColumns());
        for (IntrospectedColumn introspectedColumn : columns) {
            String actualColumnName = introspectedColumn.getActualColumnName();
            XmlElement ifElement=new XmlElement("if");
            ifElement.addAttribute(new Attribute("test", "item."+introspectedColumn.getJavaProperty()+"!=null"));
            ifElement.addElement(new TextElement(actualColumnName+"=#{item."+introspectedColumn.getJavaProperty()+",jdbcType="+introspectedColumn.getJdbcTypeName() + "},"));
            valueTrimElement.addElement(ifElement);
        }
        foreach.addElement(valueTrimElement);

        foreach.addElement(new TextElement("where "));
        List<IntrospectedColumn> primaryKeyColumns=introspectedTable.getPrimaryKeyColumns();
        //获取主键，有可能表未设置主键导致主键为空
        if(primaryKeyColumns!=null && primaryKeyColumns.size()>0){
            for (int i = 0; i < primaryKeyColumns.size(); i++) {
                IntrospectedColumn primaryKey= primaryKeyColumns.get(i);
                //如果是联合主键，可能存在多个，应该使用AND链接
                foreach.addElement(new TextElement((i>0?" AND ":"")+primaryKey.getActualColumnName()+" = #{item."+primaryKey.getJavaProperty()+",jdbcType="+primaryKey.getJdbcTypeName()+"}"));
            }
        }
        updateBatchElement.addElement(foreach);
        document.getRootElement().addElement(updateBatchElement);
        return true;
    }
}
