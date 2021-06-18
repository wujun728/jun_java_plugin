package org.mybatis.generator.plugins;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.config.TableConfiguration;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * 去掉表前缀、重命名Mapper后缀；如果在table标签中设置了domainObjectName、mapperName后以table中设置的为
 * Created by ht896632 on 2017/6/13.
 */
public class MyReplaceTablePrefix extends PluginAdapter {
    private String tablePrefix;
    private String mapperSuffix;
    private Pattern pattern;

    @Override
    public boolean validate(List<String> warnings) {
        tablePrefix = properties.getProperty("tablePrefix"); //$NON-NLS-1$
        mapperSuffix = properties.getProperty("mapperSuffix"); //$NON-NLS-1$
        boolean valid = stringHasValue(tablePrefix)
                && stringHasValue(mapperSuffix);
        if(valid){
            pattern = Pattern.compile(StringUtils.capitalize(tablePrefix.toLowerCase()));
        }else{
            if (!stringHasValue(tablePrefix)) {
                warnings.add(getString("ValidationError.99", //$NON-NLS-1$
                        "MyReplaceTablePrefix", //$NON-NLS-1$
                        "tablePrefix")); //$NON-NLS-1$
            }
            if (!stringHasValue(mapperSuffix)) {
                warnings.add(getString("ValidationError.99", //$NON-NLS-1$
                        "MyReplaceTablePrefix", //$NON-NLS-1$
                        "mapperSuffix")); //$NON-NLS-1$
            }
        }
        return valid;
    }


    @Override
    public void initialized(IntrospectedTable introspectedTable) {


        String recordType=introspectedTable.getBaseRecordType();
        Matcher matcher = pattern.matcher(recordType);
        introspectedTable.setBaseRecordType(matcher.replaceFirst(""));

        String mapperType=introspectedTable.getMyBatis3JavaMapperType();
        matcher = pattern.matcher(mapperType);
        introspectedTable.setMyBatis3JavaMapperType(matcher.replaceFirst("").replaceFirst("Mapper",mapperSuffix));

        String xmlMapper= introspectedTable.getMyBatis3XmlMapperFileName();
        matcher = pattern.matcher(xmlMapper);
        introspectedTable.setMyBatis3XmlMapperFileName(matcher.replaceFirst("").replaceFirst("Mapper",mapperSuffix));

        String exampleType=introspectedTable.getExampleType();
        matcher = pattern.matcher(exampleType);
        introspectedTable.setExampleType(matcher.replaceFirst(""));
    }
}
