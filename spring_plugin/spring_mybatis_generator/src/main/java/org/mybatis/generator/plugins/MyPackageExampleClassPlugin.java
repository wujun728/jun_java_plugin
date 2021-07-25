package org.mybatis.generator.plugins;

import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.PropertyRegistry;

import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * ExampleClass自定义存放位置
 */
public class MyPackageExampleClassPlugin extends PluginAdapter {

    private String targetPackage;
    private boolean isProject;
    private TopLevelClass compilationUnit;



    @Override
    public boolean validate(List<String> warnings) {
        targetPackage = properties.getProperty("targetPackage");
        try {
            isProject = Boolean.valueOf(properties.getProperty("isProject"));
        } catch (Exception e) {
            warnings.add(getString("ValidationError.99  true or false", //$NON-NLS-1$
                    "MyPackageExampleClassPlugin", //$NON-NLS-1$
                    "targetPackage")); //$NON-NLS-1$
        }
        boolean valid = stringHasValue(targetPackage) && stringHasValue(properties.getProperty("isProject"));
        if (!valid) {
            if (!stringHasValue(targetPackage)) {
                warnings.add(getString("ValidationError.99", //$NON-NLS-1$
                        "MyPackageExampleClassPlugin", //$NON-NLS-1$
                        "targetPackage")); //$NON-NLS-1$
            }
            if (!stringHasValue(properties.getProperty("isProject"))) {
                warnings.add(getString("ValidationError.99  true or false", //$NON-NLS-1$
                        "MyPackageExampleClassPlugin", //$NON-NLS-1$
                        "targetPackage")); //$NON-NLS-1$
            }
        }
        return valid;
    }


    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        compilationUnit=topLevelClass;
        //屏蔽自动生成的通用查询类
        return false;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        //更改通用查询类存放的目标工程
        List<GeneratedJavaFile> generatedJavaFiles = new ArrayList<GeneratedJavaFile>();
        GeneratedJavaFile generatedJavaFile = new GeneratedJavaFile(compilationUnit,
                isProject ? context.getJavaClientGeneratorConfiguration()
                        .getTargetProject(): context.getJavaModelGeneratorConfiguration()
                        .getTargetProject(),
                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                context.getJavaFormatter());
        generatedJavaFiles.add(generatedJavaFile);
        return generatedJavaFiles;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {

        introspectedTable.getGeneratedJavaFiles();
        JavaModelGeneratorConfiguration generatorConfiguration = context.getJavaModelGeneratorConfiguration();
        //获取当前目录
        String oldTargetPackage = generatorConfiguration.getTargetPackage();
        //获取通用查询类
        String oldExampleType = introspectedTable.getExampleType();
        //将旧的包名替换为新的
        String newExampleType = oldExampleType.replace(oldTargetPackage, targetPackage);
        introspectedTable.setExampleType(newExampleType);
    }

}