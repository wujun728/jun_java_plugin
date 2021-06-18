package org.mybatis.generator.plugins;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * 自定义添加注释，实体类中字段和属性默认都获取表的注释
 * Created by ht896632 on 2017/6/2.
 */
public class MyCommentGenerator implements CommentGenerator {

    /** The properties. */
    private Properties properties;

    /** The suppress date. */
    private boolean suppressDate;

    /** The suppress all comments. */
    private boolean suppressAllComments;

    /** The addition of table remark's comments.
     * If suppressAllComments is true, this option is ignored*/
    private boolean addRemarkComments;

    private SimpleDateFormat dateFormat;

    /**
     * Instantiates a new default comment generator.
     */
    public MyCommentGenerator() {
        super();
        properties = new Properties();
        suppressDate = false;
        suppressAllComments = false;
        addRemarkComments = true;
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addJavaFileComment(org.mybatis.generator.api.dom.java.CompilationUnit)
     */
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        // add no file level comments by default
    }

    /**
     * Adds a suitable comment to warn users that the element was generated, and when it was generated.
     *
     * @param xmlElement
     *            the xml element
     */
    public void addComment(XmlElement xmlElement) {
        if (suppressAllComments) {
            return;
        }

        xmlElement.addElement(new TextElement("<!--")); //$NON-NLS-1$

        StringBuilder sb = new StringBuilder();
        sb.append("  WARNING - "); //$NON-NLS-1$
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        xmlElement.addElement(new TextElement(sb.toString()));
        xmlElement
                .addElement(new TextElement(
                        "  此SQL语句为自动生成，请不要修改.")); //$NON-NLS-1$

        String s = getDateString();
        if (s != null) {
            sb.setLength(0);
            sb.append("  DATE: "); //$NON-NLS-1$
            sb.append(s);
            xmlElement.addElement(new TextElement(sb.toString()));
        }

        xmlElement.addElement(new TextElement("-->")); //$NON-NLS-1$
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addRootComment(org.mybatis.generator.api.dom.xml.XmlElement)
     */
    public void addRootComment(XmlElement rootElement) {
        // add no document level comments by default
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addConfigurationProperties(java.util.Properties)
     */
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);

        suppressDate = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));

        suppressAllComments = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));

        addRemarkComments = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_ADD_REMARK_COMMENTS));

        String dateFormatString = properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_DATE_FORMAT);
        if (StringUtility.stringHasValue(dateFormatString)) {
            dateFormat = new SimpleDateFormat(dateFormatString);
        }
    }

    /**
     * This method adds the custom javadoc tag for. You may do nothing if you do not wish to include the Javadoc tag -
     * however, if you do not include the Javadoc tag then the Java merge capability of the eclipse plugin will break.
     *
     * @param javaElement
     *            the java element
     * @param markAsDoNotDelete
     *            the mark as do not delete
     */
    protected void addJavadocTag(JavaElement javaElement,
                                 boolean markAsDoNotDelete) {
        javaElement.addJavaDocLine(" *"); //$NON-NLS-1$
        StringBuilder sb = new StringBuilder();
        sb.append(" * "); //$NON-NLS-1$
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        if (markAsDoNotDelete) {
            sb.append(" 该代码为自动生成，请不要修改"); //$NON-NLS-1$
        }
        javaElement.addJavaDocLine(sb.toString());
        String s = getDateString();
        javaElement.addJavaDocLine(" *");
        sb = new StringBuilder();
        sb.append(" * ");
        if (s != null) {
            sb.append("DATE: ");
            sb.append(s);
        }
        javaElement.addJavaDocLine(sb.toString());
    }

    /**
     * This method returns a formated date string to include in the Javadoc tag
     * and XML comments. You may return null if you do not want the date in
     * these documentation elements.
     *
     * @return a string representing the current timestamp, or null
     */
    protected String getDateString() {
        if (suppressDate) {
            return null;
        } else {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return sdf.format(new Date());
        }
    }

    /* 该注释是添加在Example上的
     * @see org.mybatis.generator.api.CommentGenerator#addClassComment(org.mybatis.generator.api.dom.java.InnerClass, org.mybatis.generator.api.IntrospectedTable)
     */
    public void addClassComment(InnerClass innerClass,
                                IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        addClassComment(innerClass,introspectedTable,false);
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addTopLevelClassComment(org.mybatis.generator.api.dom.java.TopLevelClass, org.mybatis.generator.api.IntrospectedTable)
     */
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass,
                                     IntrospectedTable introspectedTable) {
        if (suppressAllComments  || !addRemarkComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        topLevelClass.addJavaDocLine("/**"); //$NON-NLS-1$

        String remarks = introspectedTable.getRemarks();
        if (addRemarkComments && StringUtility.stringHasValue(remarks)) {
            String[] remarkLines = remarks.split(System.getProperty("line.separator"));  //$NON-NLS-1$
            for (String remarkLine : remarkLines) {
                topLevelClass.addJavaDocLine(" * " + remarkLine);  //$NON-NLS-1$
            }
        }
        topLevelClass.addJavaDocLine(" *"); //$NON-NLS-1$
        sb.append(" * TABLE:"); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable());
        topLevelClass.addJavaDocLine(sb.toString());

        addJavadocTag(topLevelClass, true);

        topLevelClass.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addEnumComment(org.mybatis.generator.api.dom.java.InnerEnum, org.mybatis.generator.api.IntrospectedTable)
     */
    public void addEnumComment(InnerEnum innerEnum,
                               IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        innerEnum.addJavaDocLine("/**"); //$NON-NLS-1$
        innerEnum.addJavaDocLine(" *");
        sb.append(" * TABLE： "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerEnum.addJavaDocLine(sb.toString());

        addJavadocTag(innerEnum, false);
        innerEnum.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addFieldComment(org.mybatis.generator.api.dom.java.Field, org.mybatis.generator.api.IntrospectedTable, org.mybatis.generator.api.IntrospectedColumn)
     */
    public void addFieldComment(Field field,
                                IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }

        if(introspectedColumn.getRemarks()!=null && !introspectedColumn.getRemarks().equals("")){
            field.addJavaDocLine("/**");
            field.addJavaDocLine(" * "+introspectedColumn.getRemarks());
            field.addJavaDocLine(" * "+introspectedTable.getFullyQualifiedTable()+"."+introspectedColumn.getActualColumnName());
            field.addJavaDocLine(" */");
        }
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addFieldComment(org.mybatis.generator.api.dom.java.Field, org.mybatis.generator.api.IntrospectedTable)
     */
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        field.addJavaDocLine("/**"); //$NON-NLS-1$

        if(!field.getName().equals("orderByClause")&& !field.getName().equals("distinct")&& !field.getName().equals("oredCriteria")){
            if(field.getName().equals("limitStart")){
                field.addJavaDocLine(" * 分页开始记录数");
            }else if(field.getName().equals("limitSize")){
                field.addJavaDocLine(" * 每页显示的记录数");
            }else{
                sb.append(" * TABLE： "); //$NON-NLS-1$
                sb.append(introspectedTable.getFullyQualifiedTable());
                field.addJavaDocLine(sb.toString());
            }
        }


        addJavadocTag(field, false);

        field.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addGeneralMethodComment(org.mybatis.generator.api.dom.java.Method, org.mybatis.generator.api.IntrospectedTable)
     */
    public void addGeneralMethodComment(Method method,
                                        IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        method.addJavaDocLine("/**"); //$NON-NLS-1$
        method
                .addJavaDocLine(" * "+operMethodComment(method,introspectedTable)+"<br>"); //$NON-NLS-1$
        method.addJavaDocLine(" *");
        method.addJavaDocLine(" * TABLE： "+introspectedTable.getFullyQualifiedTable()+"<br>");
        addJavadocTag(method, false);
        method.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    /* 实体类的get方法注释
     * @see org.mybatis.generator.api.CommentGenerator#addGetterComment(org.mybatis.generator.api.dom.java.Method, org.mybatis.generator.api.IntrospectedTable, org.mybatis.generator.api.IntrospectedColumn)
     */
    public void addGetterComment(Method method,
                                 IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }

        if (introspectedColumn.getRemarks() != null && !introspectedColumn.getRemarks().equals("")) {
            method.addJavaDocLine("/**");
            method.addJavaDocLine(" * " + introspectedColumn.getRemarks()+"<br>");
            method.addJavaDocLine(" *");
            method.addJavaDocLine(" * column：" + introspectedTable.getFullyQualifiedTable()+"."+introspectedColumn.getActualColumnName()+"<br>");
            method.addJavaDocLine(" * @return "+introspectedColumn.getActualColumnName());
            addJavadocTag(method, false);
            method.addJavaDocLine(" */");
        }

    }

    /* 实体类的set方法注释
     * @see org.mybatis.generator.api.CommentGenerator#addSetterComment(org.mybatis.generator.api.dom.java.Method, org.mybatis.generator.api.IntrospectedTable, org.mybatis.generator.api.IntrospectedColumn)
     */
    public void addSetterComment(Method method,
                                 IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        if (introspectedColumn.getRemarks() != null && !introspectedColumn.getRemarks().equals("")) {
            method.addJavaDocLine("/**");
            method.addJavaDocLine(" * " + introspectedColumn.getRemarks()+"<br>");
            method.addJavaDocLine(" *");
            method.addJavaDocLine(" * column："+introspectedTable.getFullyQualifiedTable()+"."+introspectedColumn.getActualColumnName()+"<br>");
            Parameter parm = method.getParameters().get(0);
            StringBuilder sb = new StringBuilder();
            sb.append(" * @param ");
            sb.append(parm.getName());
            method.addJavaDocLine(sb.toString());
            addJavadocTag(method, false);
            method.addJavaDocLine(" */");
        }
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.CommentGenerator#addClassComment(org.mybatis.generator.api.dom.java.InnerClass, org.mybatis.generator.api.IntrospectedTable, boolean)
     */
    public void addClassComment(InnerClass innerClass,
                                IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();

        innerClass.addJavaDocLine("/**"); //$NON-NLS-1$
        innerClass
                .addJavaDocLine(" * 此类为自动生成."); //$NON-NLS-1$

        sb.append(" * 对应的数据库表为： "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable());
        innerClass.addJavaDocLine(sb.toString());

        addJavadocTag(innerClass, markAsDoNotDelete);

        innerClass.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    private String operMethodComment(Method method,IntrospectedTable introspectedTable){
        String remarks=introspectedTable.getRemarks();
        String menthodName=method.getName();
        String result="";
        if(menthodName.indexOf("delete")>-1){
            if(menthodName.indexOf("PrimaryKey")>-1){
                result="通过主键删除"+remarks;
            }else{
                result="通过组合条件删除"+remarks+"，支持批量删除";
            }
        }else if(menthodName.indexOf("select")>-1){
            if(menthodName.indexOf("PrimaryKey")>-1){
                result="通过主键查询"+remarks;
            }else if(menthodName.indexOf("Blob")>-1){
                result="通过组合条件查询"+remarks+"，支持分页;返回全部字段";
            }else{
                result="通过组合条件查询"+remarks+"，支持分页;不返回大数据字段，如：text类型字段";
            }
        }else if(menthodName.indexOf("count")>-1){
            result="通过组合条件统计"+remarks;
        }else if(menthodName.indexOf("insert")>-1){
            result="添加数据到"+remarks;
        }else if(menthodName.indexOf("update")>-1){
            if(menthodName.indexOf("PrimaryKey")>-1){
                result="通过主键更新"+remarks;
            }else{
                result="通过组合条件更新"+remarks;
            }

        }
        return result;
    }
}
