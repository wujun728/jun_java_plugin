/*
 * Copyright 2007-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.platform.persistence.jdbc.scaffold;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.lang.BlurObject;
import net.ymate.platform.core.lang.PairObject;
import net.ymate.platform.core.support.ConsoleTableBuilder;
import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.persistence.base.EntityMeta;
import net.ymate.platform.persistence.jdbc.IDatabase;
import net.ymate.platform.persistence.jdbc.JDBC;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 持久层代码生成脚手架程序，通过已有数据库表结构逆向生成Java代码(create at 2013年9月22日下午9:44:09)
 *
 * @author 刘镇 (suninformation@163.com) on 15/12/30 下午9:30
 * @version 1.0
 */
public class EntityGenerator {

    private static final Log _LOG = LogFactory.getLog(EntityGenerator.class);

    private String __templateRootPath = EntityGenerator.class.getPackage().getName().replace(".", "/");

    private Configuration __freemarkerConfig;

    private IDatabase __owner;

    private ConfigInfo __config;

    private boolean __markdown;

    private EntityGenerator() {
        __freemarkerConfig = new Configuration(Configuration.VERSION_2_3_22);
        __freemarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        __freemarkerConfig.setClassForTemplateLoading(EntityGenerator.class, "/");
        __freemarkerConfig.setDefaultEncoding("UTF-8");
    }

    public EntityGenerator(YMP owner) {
        this();
        __owner = JDBC.get(owner == null ? YMP.get() : owner);
        //
        __config = new ConfigInfo(__owner.getOwner());
    }

    public EntityGenerator markdown() {
        __markdown = true;
        return this;
    }

    /**
     * 根据数据库表定义创建实体类文件
     *
     * @param view 指定当前创建的是视图
     * @throws Exception 可能产生的任何异常
     */
    public void createEntityClassFiles(boolean view) throws Exception {
        List<String> _tables = __config.getTableList();
        if (_tables.isEmpty()) {
            if (view) {
                _tables = TableInfo.getViewNames(__owner);
            } else {
                _tables = TableInfo.getTableNames(__owner);
            }
        }
        for (String _tableName : _tables) {
            if (checkTableNameBlacklist(_tableName)) {
                buildEntityClassFile(TableInfo.create(__owner.getDefaultConnectionHolder(), __config, _tableName, view), _tableName, view);
            }
        }
    }

    private boolean checkTableNameBlacklist(String tableName) {
        // 判断黑名单
        if (!__config.getTableExcludeList().isEmpty()) {
            if (__config.getTableExcludeList().contains(tableName.toLowerCase())) {
                return false;
            } else {
                boolean _flag = false;
                for (String _excludedName : __config.getTableExcludeList()) {
                    if (StringUtils.contains(_excludedName, "*") && StringUtils.startsWithIgnoreCase(tableName, StringUtils.substringBefore(_excludedName, "*"))) {
                        _flag = true;
                        break;
                    }
                }
                if (_flag) {
                    return false;
                }
            }
        }
        return true;
    }

    private void buildEntityClassFile(TableInfo tableInfo, String _tableName, boolean view) {
        if (tableInfo != null) {
            Map<String, Object> _properties = __config.toMap();
            if (__config.isUseBaseEntity()) {
                buildTargetFile("/model/BaseEntity.java", "/BaseEntity.ftl", _properties);
            }
            PairObject<String, String> _fixedName = __config.buildNamePrefix(_tableName);
            //
            _properties.put("modelName", _fixedName.getKey());
            _properties.put("tableName", _fixedName.getValue());
            //
            List<Attr> _fieldList = new ArrayList<Attr>(); // 用于完整的构造方法
            List<Attr> _fieldListForNotNullable = new ArrayList<Attr>(); // 用于非空字段的构造方法
            List<Attr> _allFieldList = new ArrayList<Attr>(); // 用于生成字段名称常量
            if (tableInfo.getPkSet().size() > 1) {
                _properties.put("primaryKeyType", _fixedName.getKey() + "PK");
                _properties.put("primaryKeyName", StringUtils.uncapitalize((String) _properties.get("primaryKeyType")));
                List<Attr> _primaryKeyList = new ArrayList<Attr>();
                _properties.put("primaryKeyList", _primaryKeyList);
                Attr _pkAttr = new Attr((String) _properties.get("primaryKeyType"), (String) _properties.get("primaryKeyName"), null, false, false, 0, 0, false, null, null);
                _fieldList.add(_pkAttr);
                _fieldListForNotNullable.add(_pkAttr);
                //
                for (String pkey : tableInfo.getPkSet()) {
                    ColumnInfo _ci = tableInfo.getFieldMap().get(pkey);
                    Attr _attr = _ci.toAttr();
                    if (__config.getReadonlyFields().contains(_attr.getColumnName().toLowerCase())) {
                        _attr.setReadonly(true);
                    }
                    _primaryKeyList.add(_attr);
                    _allFieldList.add(new Attr("String", __config.namedFilter(_ci.getColumnName()).toUpperCase(),
                            _ci.getColumnName(),
                            _ci.isAutoIncrement(),
                            _ci.isSigned(),
                            _ci.getPrecision(),
                            _ci.getScale(),
                            _ci.isNullable(),
                            _ci.getDefaultValue(),
                            _ci.getRemarks()));
                }
                for (String key : tableInfo.getFieldMap().keySet()) {
                    if (tableInfo.getPkSet().contains(key)) {
                        continue;
                    }
                    ColumnInfo _ci = tableInfo.getFieldMap().get(key);
                    Attr _attr = _ci.toAttr();
                    if (__config.getReadonlyFields().contains(_attr.getColumnName().toLowerCase())) {
                        _attr.setReadonly(true);
                    }
                    _fieldList.add(_attr);
                    _fieldListForNotNullable.add(_attr);
                    _allFieldList.add(new Attr("String", __config.namedFilter(_ci.getColumnName()).toUpperCase(),
                            _ci.getColumnName(),
                            _ci.isAutoIncrement(),
                            _ci.isSigned(),
                            _ci.getPrecision(),
                            _ci.getScale(),
                            _ci.isNullable(),
                            _ci.getDefaultValue(),
                            _ci.getRemarks()));
                }
            } else {
                if (!view) {
                    _properties.put("primaryKeyType", tableInfo.getFieldMap().get(tableInfo.getPkSet().get(0)).getColumnType());
                    _properties.put("primaryKeyName", StringUtils.uncapitalize(EntityMeta.propertyNameToFieldName(tableInfo.getPkSet().get(0))));
                } else {
                    ColumnInfo _tmpCI = tableInfo.getFieldMap().get("id");
                    _properties.put("primaryKeyType", _tmpCI == null ? "Serializable" : _tmpCI.getColumnType());
                    _properties.put("primaryKeyName", "id");
                }
                for (String key : tableInfo.getFieldMap().keySet()) {
                    ColumnInfo _ci = tableInfo.getFieldMap().get(key);
                    Attr _attr = _ci.toAttr();
                    if (__config.getReadonlyFields().contains(_attr.getColumnName().toLowerCase())) {
                        _attr.setReadonly(true);
                    }
                    _fieldList.add(_attr);
                    if (_attr.isNullable()) {
                        _fieldListForNotNullable.add(_attr);
                    }
                    _allFieldList.add(new Attr("String", __config.namedFilter(_ci.getColumnName()).toUpperCase(),
                            _ci.getColumnName(),
                            _ci.isAutoIncrement(),
                            _ci.isSigned(),
                            _ci.getPrecision(),
                            _ci.getScale(),
                            _ci.isNullable(),
                            _ci.getDefaultValue(),
                            _ci.getRemarks()));
                }
            }
            _properties.put("fieldList", _fieldList);
            // 为必免构造方法重复，构造参数数量相同则清空
            _properties.put("notNullableFieldList", _fieldList.size() == _fieldListForNotNullable.size() ? Collections.emptyList() : _fieldListForNotNullable);
            _properties.put("allFieldList", _allFieldList);
            //
            buildTargetFile("/model/" + _fixedName.getKey() + (__config.isUseClassSuffix() ? "Model.java" : ".java"), view ? "/View.ftl" : "/Entity.ftl", _properties);
            //
            if (!view) {
                if (tableInfo.getPkSet().size() > 1) {
                    _properties.put("modelName", _fixedName.getKey());
                    if (tableInfo.getPkSet().size() > 1) {
                        List<Attr> _primaryKeyList = new ArrayList<Attr>();
                        _properties.put("primaryKeyList", _primaryKeyList);
                        //
                        for (String pkey : tableInfo.getPkSet()) {
                            ColumnInfo _ci = tableInfo.getFieldMap().get(pkey);
                            _primaryKeyList.add(_ci.toAttr());
                        }
                    }
                    buildTargetFile("/model/" + _fixedName.getKey() + "PK.java", "/EntityPK.ftl", _properties);
                }
            }
            //
            ConsoleTableBuilder _console = ConsoleTableBuilder.create(10);
            System.out.println((view ? "VIEW" : "TABLE") + "_NAME: " + _tableName);
            System.out.println("MODEL_NAME: " + _fixedName.getKey());
            //
            if (__markdown) {
                _console.markdown();
                System.out.println();
            }
            //
            _console.addRow().addColumn("COLUMN_NAME")
                    .addColumn("COLUMN_CLASS_NAME")
                    .addColumn("PRIMARY_KEY")
                    .addColumn("AUTO_INCREMENT")
                    .addColumn("SIGNED")
                    .addColumn("PRECISION")
                    .addColumn("SCALE")
                    .addColumn("NULLABLE")
                    .addColumn("DEFAULT")
                    .addColumn("REMARKS");
            for (ColumnInfo _c : tableInfo.getFieldMap().values()) {
                _console.addRow().addColumn(_c.getColumnName())
                        .addColumn(_c.getColumnType())
                        .addColumn(_c.isPrimaryKey() ? "TRUE" : "FALSE")
                        .addColumn(_c.isAutoIncrement() ? "TRUE" : "FALSE")
                        .addColumn(_c.isSigned() ? "TRUE" : "FALSE")
                        .addColumn(_c.getPrecision() + "")
                        .addColumn(_c.getScale() + "")
                        .addColumn(_c.isNullable() ? "TRUE" : "FALSE")
                        .addColumn(_c.getDefaultValue())
                        .addColumn(_c.getRemarks());
            }
            System.out.println(_console.toString());
        }
    }

    private void buildTargetFile(String targetFileName, String tmplFile, Map<String, Object> properties) {
        Writer _outWriter = null;
        try {
            File _outputFile = new File(__config.getOutputPath(), new File(__config.getPackageName().replace('.', '/'), targetFileName).getPath());
            File _path = _outputFile.getParentFile();
            if (_path.exists() || _path.mkdirs()) {
                _outWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(_outputFile), StringUtils.defaultIfEmpty(__freemarkerConfig.getOutputEncoding(), __freemarkerConfig.getDefaultEncoding())));
                __freemarkerConfig.getTemplate(__templateRootPath + tmplFile).process(properties, _outWriter);
                //
                System.out.println("Output file " + _outputFile);
            }
        } catch (Exception e) {
            _LOG.warn("", RuntimeUtils.unwrapThrow(e));
        } finally {
            IOUtils.closeQuietly(_outWriter);
        }
    }

    public static void main(String[] args) throws Exception {
        YMP.get().init();
        try {
            new EntityGenerator(YMP.get()).createEntityClassFiles((args != null && args.length > 0) && BlurObject.bind(args[0]).toBooleanValue());
        } finally {
            YMP.get().destroy();
        }
    }
}
