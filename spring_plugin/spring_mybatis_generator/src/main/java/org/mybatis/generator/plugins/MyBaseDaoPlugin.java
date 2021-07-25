package org.mybatis.generator.plugins;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.JavaFormatter;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
import org.mybatis.generator.api.dom.java.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * BaseDao插件，自动生成BaseDao父类，如果已经存在则不进行创建
 * Created by ht896632 on 2017/6/2.
 */
public class MyBaseDaoPlugin extends PluginAdapter {
    private String name="";
    private String targetPackage ="";
    private String targetProject="";


    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
    }

    public boolean validate(List<String> warnings) {
        targetPackage =properties.get("targetPackage").toString();
        targetProject=properties.get("targetProject").toString();
        name=properties.get("name").toString();
        boolean valid = stringHasValue(targetPackage)
                && stringHasValue(targetProject)  && stringHasValue(name);
        if(!valid){
            if (!stringHasValue(targetPackage)) {
                warnings.add(getString("ValidationError.99", //$NON-NLS-1$
                        "MyBaseDaoPlugin", //$NON-NLS-1$
                        "targetPackage")); //$NON-NLS-1$
            }
            if (!stringHasValue(targetProject)) {
                warnings.add(getString("ValidationError.99", //$NON-NLS-1$
                        "MyBaseDaoPlugin", //$NON-NLS-1$
                        "targetProject")); //$NON-NLS-1$
            }
            if (!stringHasValue(targetProject)) {
                warnings.add(getString("ValidationError.99", //$NON-NLS-1$
                        "MyBaseDaoPlugin", //$NON-NLS-1$
                        "name")); //$NON-NLS-1$
            }
        }
		return true;
	}


    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        List<GeneratedJavaFile> files = new ArrayList<GeneratedJavaFile>();
        //获取当前目录
        String baseRecordType= introspectedTable.getBaseRecordType();
        String baseRecordName=baseRecordType.substring(baseRecordType.lastIndexOf(".")+1,baseRecordType.length());
        String replaceName=introspectedTable.getExampleType();
        replaceName= replaceName.substring(replaceName.lastIndexOf(".")+1,replaceName.length()).replace(baseRecordName,"");
        files.add(generatedBaseDaoFile(replaceName));

        return files;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String columnsType="java.lang.String";
        //动态获取当前表的主键类型，如果有联合主键，默认取联合主键的第一列（可能存在问题）
        //主键为空则默认为String类型
        if(introspectedTable.getPrimaryKeyColumns()!=null && introspectedTable.getPrimaryKeyColumns().size()>0){
            columnsType=introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().toString();
        }
        /**
         * 主键默认采用java.lang.Integer
         */
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(name+"<" + introspectedTable.getBaseRecordType()
                + "," + introspectedTable.getExampleType() + "," + columnsType + ">");

        FullyQualifiedJavaType imp = new FullyQualifiedJavaType(targetPackage+"."+name);
        /**
         * 添加 extends MybatisBaseMapper
         */
        interfaze.addSuperInterface(fqjt);

        /**
         * 添加import my.mabatis.example.base.MybatisBaseMapper;
         */
        interfaze.addImportedType(imp);
        /**
         * 方法不需要
         */
        interfaze.getMethods().clear();
        interfaze.getAnnotations().clear();
        return true;
    }

    /**
     * 生成BaseService接口
     * @return
     */
    private GeneratedJavaFile generatedBaseDaoFile(String replaceName) {

        String domainName =name+"<T, E, PK extends Serializable>";
        FullyQualifiedJavaType serviceInterfaceType = new FullyQualifiedJavaType(targetPackage + "." + domainName);
        Interface service = new Interface(serviceInterfaceType);
        service.setVisibility(JavaVisibility.PUBLIC);
        service.addImportedType(new FullyQualifiedJavaType("java.util.List"));
        service.addImportedType(new FullyQualifiedJavaType("java.io.Serializable"));
        service.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Param"));

        // long countByExample(E example);
        String example=replaceName;
        String paramExample=example.toLowerCase();
        Method method=new Method();
        method.setName("countBy"+example);
        method.setReturnType(new FullyQualifiedJavaType("long"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("E"),paramExample));
        service.addMethod(method);

        //int deleteByExample(E example);
        method=new Method();
        method.setName("deleteBy"+example);
        method.setReturnType(new FullyQualifiedJavaType("int"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("E"),paramExample));
        service.addMethod(method);

        //int deleteByPrimaryKey(PK id);
        method=new Method();
        method.setName("deleteByPrimaryKey");
        method.setReturnType(new FullyQualifiedJavaType("int"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("PK"),"id"));
        service.addMethod(method);

        //int insertSelective(T record);
        method=new Method();
        method.setName("insertSelective");
        method.setReturnType(new FullyQualifiedJavaType("int"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("T"),"record"));
        service.addMethod(method);

        method=new Method();
        method.setName("insertBatchSelective");
        method.setReturnType(new FullyQualifiedJavaType("int"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("List<T>"),"records"));
        service.addMethod(method);


        //List<T> selectByExample(E example);
        method=new Method();
        method.setName("selectBy"+example);
        method.setReturnType(new FullyQualifiedJavaType("List<T>"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("E"),paramExample));
        service.addMethod(method);


        //T selectByPrimaryKey(PK id);
        method=new Method();
        method.setName("selectByPrimaryKey");
        method.setReturnType(new FullyQualifiedJavaType("T"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("PK"),"id"));
        service.addMethod(method);

        method=new Method();
        method.setName("selectSingleBy"+example);
        method.setReturnType(new FullyQualifiedJavaType("T"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("E"),paramExample));
        service.addMethod(method);

        //int updateByExampleSelective(T record, E example);
        method=new Method();
        method.setName("updateBy"+example+"Selective");
        method.setReturnType(new FullyQualifiedJavaType("int"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("@Param(\"record\") T"),"record"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("@Param(\""+paramExample+"\") E"),paramExample));
        service.addMethod(method);

        //int updateByPrimaryKeySelective(T record);
        method=new Method();
        method.setName("updateByPrimaryKeySelective");
        method.setReturnType(new FullyQualifiedJavaType("int"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("T"),"record"));
        service.addMethod(method);

//        int updateBatchByPrimaryKeySelective(List<MaterialCopy> records);
        method=new Method();
        method.setName("updateBatchByPrimaryKeySelective");
        method.setReturnType(new FullyQualifiedJavaType("int"));
        method.addParameter(new Parameter(new FullyQualifiedJavaType("List<T>"),"records"));
        service.addMethod(method);

        JavaFormatter javaFormatter = new DefaultJavaFormatter();
        javaFormatter.setContext(context);
        return new GeneratedJavaFile(service,targetProject,javaFormatter);
    }
}
