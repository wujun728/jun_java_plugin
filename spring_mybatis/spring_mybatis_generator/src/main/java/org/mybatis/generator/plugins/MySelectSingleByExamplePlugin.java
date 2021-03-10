package org.mybatis.generator.plugins;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.*;

import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * 单个对象查询插件，可根据不同字段进行查询
 */
public class MySelectSingleByExamplePlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }


    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String baseRecordType= introspectedTable.getBaseRecordType();
        String baseRecordName=baseRecordType.substring(baseRecordType.lastIndexOf(".")+1,baseRecordType.length());
        String replaceName=introspectedTable.getExampleType();
        replaceName= replaceName.substring(replaceName.lastIndexOf(".")+1,replaceName.length()).replace(baseRecordName,"");

        createSelectSingleMethod(interfaze, introspectedTable, replaceName);
        return true;
    }

    private void createSelectSingleMethod(Interface interfaze, IntrospectedTable introspectedTable, String replaceName) {
        Method method=new Method();
        method.setName("selectSingleBy"+replaceName);
        method.setReturnType(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));

        Parameter parameter=new Parameter(new FullyQualifiedJavaType(introspectedTable.getExampleType()),replaceName.toLowerCase());
        method.addParameter(parameter);
        interfaze.addMethod(method);
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        String fqjt = introspectedTable.getExampleType();
        String baseRecordType= introspectedTable.getBaseRecordType();
        String baseRecordName=baseRecordType.substring(baseRecordType.lastIndexOf(".")+1,baseRecordType.length());
        String replaceName=introspectedTable.getExampleType();
        replaceName= replaceName.substring(replaceName.lastIndexOf(".")+1,replaceName.length()).replace(baseRecordName,"");

        XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

        answer.addAttribute(new Attribute("id", //$NON-NLS-1$
                "selectSingleBy"+replaceName));
        answer.addAttribute(new Attribute(
                "resultMap", introspectedTable.getBaseResultMapId())); //$NON-NLS-1$
        answer.addAttribute(new Attribute("parameterType", fqjt)); //$NON-NLS-1$

        context.getCommentGenerator().addComment(answer);

        answer.addElement(new TextElement("select")); //$NON-NLS-1$
        XmlElement ifElement = new XmlElement("if"); //$NON-NLS-1$
        ifElement.addAttribute(new Attribute("test", "distinct")); //$NON-NLS-1$ //$NON-NLS-2$
        ifElement.addElement(new TextElement("distinct")); //$NON-NLS-1$
        answer.addElement(ifElement);

        StringBuilder sb = new StringBuilder();
        if (stringHasValue(introspectedTable
                .getSelectByExampleQueryId())) {
            sb.append('\'');
            sb.append(introspectedTable.getSelectByExampleQueryId());
            sb.append("' as QUERYID,"); //$NON-NLS-1$
            answer.addElement(new TextElement(sb.toString()));
        }

        XmlElement baseColumnListElement = new XmlElement("include"); //$NON-NLS-1$
        baseColumnListElement.addAttribute(new Attribute("refid", //$NON-NLS-1$
                introspectedTable.getBaseColumnListId()));
        answer.addElement(baseColumnListElement);

        sb.setLength(0);
        sb.append("from "); //$NON-NLS-1$
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());

        XmlElement parameterElement = new XmlElement("if"); //$NON-NLS-1$
        parameterElement.addAttribute(new Attribute("test", "_parameter != null")); //$NON-NLS-1$ //$NON-NLS-2$

        XmlElement parameterIncludeElement = new XmlElement("include"); //$NON-NLS-1$
        parameterIncludeElement.addAttribute(new Attribute("refid", //$NON-NLS-1$
                introspectedTable.getExampleWhereClauseId()));
        parameterElement.addElement(parameterIncludeElement);


        answer.addElement(new TextElement(sb.toString()));
        answer.addElement(parameterElement);

        ifElement = new XmlElement("if"); //$NON-NLS-1$
        ifElement.addAttribute(new Attribute("test", "orderByClause != null")); //$NON-NLS-1$ //$NON-NLS-2$
        ifElement.addElement(new TextElement("order by ${orderByClause}")); //$NON-NLS-1$
        answer.addElement(ifElement);
        document.getRootElement().addElement(answer);
        return true;
    }
}
