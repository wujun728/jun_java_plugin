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

import net.ymate.platform.core.IConfig;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.lang.BlurObject;
import net.ymate.platform.core.lang.PairObject;
import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.persistence.base.EntityMeta;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 刘镇 (suninformation@163.com) on 2017/10/19 下午5:27
 * @version 1.0
 */
public class ConfigInfo {

    private String dbName;

    private String dbUserName;

    private boolean removePrefix;

    private List<String> tablePrefixes;

    private List<String> tableList;

    private List<String> tableExcludeList;

    private boolean useBaseEntity;

    private boolean useClassSuffix;

    private boolean useChainMode;

    private boolean useStateSupport;

    private IEntityNamedFilter namedFilter;

    private List<String> readonlyFields;

    private String packageName;

    private String outputPath;

    public ConfigInfo(YMP owner) {
        IConfig _config = owner.getConfig();
        //
        this.useBaseEntity = BlurObject.bind(_config.getParam("jdbc.use_base_entity")).toBooleanValue();
        this.useClassSuffix = BlurObject.bind(_config.getParam("jdbc.use_class_suffix")).toBooleanValue();
        this.useChainMode = BlurObject.bind(_config.getParam("jdbc.use_chain_mode")).toBooleanValue();
        this.useStateSupport = BlurObject.bind(_config.getParam("jdbc.use_state_support")).toBooleanValue();
        this.packageName = StringUtils.defaultIfBlank(_config.getParam("jdbc.package_name"), "packages");
        this.outputPath = RuntimeUtils.replaceEnvVariable(StringUtils.defaultIfBlank(owner.getConfig().getParam("jdbc.output_path"), "${root}"));
        this.dbName = _config.getParam("jdbc.db_name");
        this.dbUserName = _config.getParam("jdbc.db_username");
        this.tablePrefixes = Arrays.asList(StringUtils.split(StringUtils.trimToEmpty(_config.getParam("jdbc.table_prefix")), '|'));
        this.removePrefix = new BlurObject(_config.getParam("jdbc.remove_table_prefix")).toBooleanValue();
        this.tableExcludeList = Arrays.asList(StringUtils.split(StringUtils.trimToEmpty(_config.getParam("jdbc.table_exclude_list")).toLowerCase(), "|"));
        this.tableList = Arrays.asList(StringUtils.split(StringUtils.trimToEmpty(_config.getParam("jdbc.table_list")), "|"));
        //
        this.namedFilter = ClassUtils.impl(_config.getParam("jdbc.named_filter_class"), IEntityNamedFilter.class, this.getClass());
        this.readonlyFields = Arrays.asList(StringUtils.split(StringUtils.trimToEmpty(_config.getParam("jdbc.readonly_field_list")).toLowerCase(), '|'));
    }

    public ConfigInfo(String dbName, String dbUserName, boolean removePrefix, List<String> tablePrefixes, List<String> tableList, List<String> tableExcludeList, boolean useBaseEntity, boolean useClassSuffix, boolean useChainMode, boolean useStateSupport, IEntityNamedFilter namedFilter, List<String> readonlyFields, String packageName, String outputPath) {
        this.dbName = dbName;
        this.dbUserName = dbUserName;
        this.removePrefix = removePrefix;
        this.tablePrefixes = tablePrefixes;
        this.tableList = tableList;
        this.tableExcludeList = tableExcludeList;
        this.useBaseEntity = useBaseEntity;
        this.useClassSuffix = useClassSuffix;
        this.useChainMode = useChainMode;
        this.useStateSupport = useStateSupport;
        this.namedFilter = namedFilter;
        this.readonlyFields = readonlyFields;
        this.packageName = StringUtils.defaultIfBlank(packageName, "packages");
        this.outputPath = RuntimeUtils.replaceEnvVariable(StringUtils.defaultIfBlank(outputPath, "${root}"));
    }

    public PairObject<String, String> buildNamePrefix(String tableName) {
        String _modelName = null;
        for (String _prefix : tablePrefixes) {
            if (tableName.startsWith(_prefix)) {
                if (removePrefix) {
                    tableName = tableName.substring(_prefix.length());
                }
                _modelName = StringUtils.capitalize(EntityMeta.propertyNameToFieldName(namedFilter(tableName)));
                break;
            }
        }
        if (StringUtils.isBlank(_modelName)) {
            _modelName = StringUtils.capitalize(EntityMeta.propertyNameToFieldName(namedFilter(tableName)));
        }
        return new PairObject<String, String>(_modelName, tableName);
    }

    public String namedFilter(String original) {
        if (this.namedFilter != null) {
            return StringUtils.defaultIfBlank(this.namedFilter.doFilter(original), original);
        }
        return original;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> _returnValue = ClassUtils.wrapper(this).toMap(new ClassUtils.IFieldValueFilter() {

            private List<String> __fields = Arrays.asList("packageName", "useBaseEntity", "useClassSuffix", "useChainMode", "useStateSupport");

            @Override
            public boolean filter(String fieldName, Object fieldValue) {
                return !__fields.contains(fieldName);
            }
        });
        _returnValue.put("lastUpdateTime", new Date());
        //
        return _returnValue;
    }

    public String getDbName() {
        return dbName;
    }

    public String getDbUserName() {
        return dbUserName;
    }

    public boolean isRemovePrefix() {
        return removePrefix;
    }

    public List<String> getTablePrefixes() {
        return tablePrefixes;
    }

    public List<String> getTableList() {
        return tableList;
    }

    public List<String> getTableExcludeList() {
        return tableExcludeList;
    }

    public boolean isUseBaseEntity() {
        return useBaseEntity;
    }

    public boolean isUseClassSuffix() {
        return useClassSuffix;
    }

    public boolean isUseChainMode() {
        return useChainMode;
    }

    public boolean isUseStateSupport() {
        return useStateSupport;
    }

    public IEntityNamedFilter getNamedFilter() {
        return namedFilter;
    }

    public List<String> getReadonlyFields() {
        return readonlyFields;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getOutputPath() {
        return outputPath;
    }
}
