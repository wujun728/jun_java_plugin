package org.mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * 重命名Dao方法中和mapper中的方法名称
 */
public class MyRenameExampleMethodPlugin extends PluginAdapter {
    private String searchString;
    private String replaceString;

    @Override
    public boolean validate(List<String> warnings) {
        searchString = properties.getProperty("searchString"); //$NON-NLS-1$
        replaceString = properties.getProperty("replaceString");
        boolean valid = stringHasValue(searchString)
                && stringHasValue(replaceString);
        if (!valid) {
            if (!stringHasValue(searchString)) {
                warnings.add(getString("ValidationError.99", //$NON-NLS-1$
                        "MyRenameExampleMethodPlugin", //$NON-NLS-1$
                        "searchString")); //$NON-NLS-1$
            }
            if (!stringHasValue(replaceString)) {
                warnings.add(getString("ValidationError.99", //$NON-NLS-1$
                        "MyRenameExampleMethodPlugin", //$NON-NLS-1$
                        "replaceString")); //$NON-NLS-1$
            }
        }
        return valid;
    }

    @Override
    public boolean clientCountByExampleMethodGenerated(Method method,
                                                       Interface interfaze, IntrospectedTable introspectedTable) {
        //重命名countByEx方法和参数
        method.setName(introspectedTable.getCountByExampleStatementId().replace(searchString, replaceString));
        List<Parameter> parameterList = method.getParameters();
        for (Parameter parameter : parameterList) {
            if (parameter.getName().equals("example")) {
                parameterList.remove(parameter);
                break;
            }
        }
        parameterList.add(new Parameter(new FullyQualifiedJavaType(
                introspectedTable.getExampleType()), replaceString.toLowerCase()));
        return true;
    }

    @Override
    public boolean sqlMapCountByExampleElementGenerated(XmlElement answer, IntrospectedTable introspectedTable) {

        List<Attribute> attributes = answer.getAttributes();
        for (Attribute attribute : attributes) {
            if (attribute.getName().equals("id")) {
                attributes.remove(attribute);
                break;
            }
        }
        attributes.add(new Attribute(
                "id", introspectedTable.getCountByExampleStatementId().replace(searchString, replaceString)));
        return true;
    }


    @Override
    public boolean clientDeleteByExampleMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        //重命名deleteByExample方法和参数
        method.setName(introspectedTable.getDeleteByExampleStatementId().replace(searchString, replaceString));
        List<Parameter> parameterList = method.getParameters();
        for (Parameter parameter : parameterList) {
            if (parameter.getName().equals("example")) {
                parameterList.remove(parameter);
                break;
            }
        }
        parameterList.add(new Parameter(new FullyQualifiedJavaType(
                introspectedTable.getExampleType()), replaceString.toLowerCase()));
        return true;
    }

    @Override
    public boolean sqlMapDeleteByExampleElementGenerated(XmlElement answer, IntrospectedTable introspectedTable) {
        List<Attribute> attributes = answer.getAttributes();
        for (Attribute attribute : attributes) {
            if (attribute.getName().equals("id")) {
                attributes.remove(attribute);
                break;
            }
        }
        attributes.add(new Attribute(
                "id", introspectedTable.getDeleteByExampleStatementId().replace(searchString, replaceString)));
        return true;
    }

    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method,
                                                                    Interface interfaze, IntrospectedTable introspectedTable) {
        //重命名selectByExample方法和参数
        method.setName(introspectedTable.getSelectByExampleStatementId().replace(searchString, replaceString));

        List<Parameter> parameterList = method.getParameters();
        for (Parameter parameter : parameterList) {
            if (parameter.getName().equals("example")) {
                parameterList.remove(parameter);
                break;
            }
        }
        parameterList.add(new Parameter(new FullyQualifiedJavaType(
                introspectedTable.getExampleType()), replaceString.toLowerCase()));
        return true;
    }


    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement answer, IntrospectedTable introspectedTable) {
        List<Attribute> attributes = answer.getAttributes();
        for (Attribute attribute : attributes) {
            if (attribute.getName().equals("id")) {
                attributes.remove(attribute);
                break;
            }
        }
        attributes.add(new Attribute(
                "id", introspectedTable.getSelectByExampleStatementId().replace(searchString, replaceString)));

        return true;
    }

    @Override
    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {

        method.setName(introspectedTable
                .getSelectByExampleWithBLOBsStatementId().replace(searchString, replaceString));
        List<Parameter> parameterList = method.getParameters();
        for (Parameter parameter : parameterList) {
            if (parameter.getName().equals("example")) {
                parameterList.remove(parameter);
                break;
            }
        }
        parameterList.add(new Parameter(new FullyQualifiedJavaType(
                introspectedTable.getExampleType()), replaceString.toLowerCase()));
        return true;
    }

    @Override
    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement answer, IntrospectedTable introspectedTable) {
        List<Attribute> attributes = answer.getAttributes();
        for (Attribute attribute : attributes) {
            if (attribute.getName().equals("id")) {
                attributes.remove(attribute);
                break;
            }
        }
        attributes.add(new Attribute(
                "id", introspectedTable.getSelectByExampleWithBLOBsStatementId().replace(searchString, replaceString)));
        return true;
    }

    @Override
    public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method,
                                                                 Interface interfaze, IntrospectedTable introspectedTable) {
        //重命名updateByExampleSelective方法和参数
        method.setName(introspectedTable
                .getUpdateByExampleSelectiveStatementId().replace(searchString, replaceString));
        List<Parameter> parameterList = method.getParameters();
        for (Parameter parameter : parameterList) {
            if (parameter.getName().equals("example")) {
                parameterList.remove(parameter);
                break;
            }
        }
        parameterList.add(new Parameter(new FullyQualifiedJavaType(
                introspectedTable.getExampleType()),
                replaceString.toLowerCase(), "@Param(\"" + replaceString.toLowerCase() + "\")"));
        return true;
    }

    @Override
    public boolean sqlMapUpdateByExampleSelectiveElementGenerated(XmlElement answer, IntrospectedTable introspectedTable) {
        List<Attribute> attributes = answer.getAttributes();
        for (Attribute attribute : attributes) {
            if (attribute.getName().equals("id")) {
                attributes.remove(attribute);
                break;
            }
        }
        attributes.add(new Attribute(
                "id", introspectedTable.getUpdateByExampleSelectiveStatementId().replace(searchString, replaceString)));
        return true;
    }

    @Override
    public boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        method.setName(introspectedTable
                .getUpdateByExampleWithBLOBsStatementId().replace(searchString, replaceString));
        List<Parameter> parameterList = method.getParameters();
        for (Parameter parameter : parameterList) {
            if (parameter.getName().equals("example")) {
                parameterList.remove(parameter);
                break;
            }
        }
        parameterList.add(new Parameter(new FullyQualifiedJavaType(
                introspectedTable.getExampleType()),
                replaceString.toLowerCase(), "@Param(\"" + replaceString.toLowerCase() + "\")"));
        return true;
    }

    @Override
    public boolean sqlMapUpdateByExampleWithBLOBsElementGenerated(XmlElement answer, IntrospectedTable introspectedTable) {
        List<Attribute> attributes = answer.getAttributes();
        for (Attribute attribute : attributes) {
            if (attribute.getName().equals("id")) {
                attributes.remove(attribute);
                break;
            }
        }
        attributes.add(new Attribute("id", //$NON-NLS-1$
                introspectedTable.getUpdateByExampleWithBLOBsStatementId().replace(searchString, replaceString)));
        return true;
    }

    @Override
    public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {

        method.setName(introspectedTable.getUpdateByExampleStatementId().replace(searchString, replaceString));
        List<Parameter> parameterList = method.getParameters();
        for (Parameter parameter : parameterList) {
            if (parameter.getName().equals("example")) {
                parameterList.remove(parameter);
                break;
            }
        }
        parameterList.add(new Parameter(new FullyQualifiedJavaType(
                introspectedTable.getExampleType()),
                replaceString.toLowerCase(), "@Param(\"" + replaceString.toLowerCase() + "\")"));
        return true;
    }

    @Override
    public boolean sqlMapUpdateByExampleWithoutBLOBsElementGenerated(XmlElement answer, IntrospectedTable introspectedTable) {
        List<Attribute> attributes = answer.getAttributes();
        for (Attribute attribute : attributes) {
            if (attribute.getName().equals("id")) {
                attributes.remove(attribute);
                break;
            }
        }
        attributes.add(new Attribute("id", //$NON-NLS-1$
                introspectedTable.getUpdateByExampleStatementId().replace(searchString, replaceString)));
        return true;
    }

    @Override
    public boolean sqlMapExampleWhereClauseElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        List<Attribute> attributes = element.getAttributes();
        //重命名SqlMap中Update_By_Example_Where_Clause中的example.oredCriteria
        if (attributes.get(0).getValue().equals(introspectedTable.getMyBatis3UpdateByExampleWhereClauseId())) {
            List<Element> elementList = element.getElements();
            XmlElement element1=null;
            if(elementList!=null && !elementList.isEmpty()){
                for (Element  element2 : elementList) {
                    if(element2 instanceof XmlElement){
                        element1 = (XmlElement) element2;
                        if(element1.getName().equals("where")){
                            break;
                        }
                    }
                }
            }

            List<Element> element1Elements = element1.getElements();
            XmlElement element12 = (XmlElement) element1Elements.get(0);
            List<Attribute> attributes2 = element12.getAttributes();
            for (Attribute attribute : attributes2) {
                if (attribute.getName().equals("collection")) {
                    attributes2.remove(attribute);
                    break;
                }
            }
            attributes2.add(new Attribute(
                    "collection", "example.oredCriteria".replace(searchString.toLowerCase(), replaceString.toLowerCase())));
        }
        return true;
    }
}
