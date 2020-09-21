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
package net.ymate.platform.persistence.base;

import net.ymate.platform.core.lang.PairObject;
import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.persistence.annotation.*;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据实体属性描述对象
 *
 * @author 刘镇 (suninformation@163.com) on 2014年2月16日 下午2:20:48
 * @version 1.0
 */
public final class EntityMeta {

    private static final Map<Class<? extends IEntity>, EntityMeta> __entityMetas;

    static {
        __entityMetas = new ConcurrentHashMap<Class<? extends IEntity>, EntityMeta>();
    }

    /**
     * 实体名称
     */
    private String __entityName;

    /**
     * 数据分片(表)规则注解
     */
    private ShardingRule __shardingRuleAnno;

    /**
     * 主键类型
     */
    private Class<?> __primaryKeyClass;

    /**
     * 主键字段名称集合
     */
    private List<String> __primaryKeys;

    /**
     * 自动增长的字段名称集合
     */
    private List<String> __autoincrementProps;

    /**
     * 只读字段名称集合
     */
    private List<String> __readonlyProps;

    /**
     * 字段->属性映射
     */
    private Map<String, PropertyMeta> __properties;

    /**
     * 属性->字段映射
     */
    private Map<String, PropertyMeta> __fields;

    /**
     * 索引名称->索引映射
     */
    private Map<String, IndexMeta> __indexes;

    /**
     * 是否为复合主键
     */
    private boolean __multiplePrimaryKey;

    /**
     * 是否为视图
     */
    private boolean __view;

    /**
     * 实体注释说明信息
     */
    private String __comment;

    /**
     * @param targetClass 目标实体类对象
     * @return 创建数据实体属性描述对象
     */
    public static EntityMeta createAndGet(Class<? extends IEntity> targetClass) {
        EntityMeta _returnMeta = __entityMetas.get(targetClass);
        if (_returnMeta == null) {
            // 判断clazz对象是否声明了@Entity注解
            if (ClassUtils.isAnnotationOf(targetClass, Entity.class)) {
                _returnMeta = new EntityMeta(StringUtils.defaultIfBlank(targetClass.getAnnotation(Entity.class).value(), fieldNameToPropertyName(targetClass.getSimpleName(), 0)), ClassUtils.isAnnotationOf(targetClass, Readonly.class), targetClass.getAnnotation(ShardingRule.class));
                // 判断clazz对象是否声明了@Comment注解
                if (ClassUtils.isAnnotationOf(targetClass, Comment.class)) {
                    _returnMeta.__comment = targetClass.getAnnotation(Comment.class).value();
                }
                // 处理字段属性
                __doParseProperties(targetClass, _returnMeta);
                // 处理主键(排除视图)
                if (!_returnMeta.isView()) {
                    __doParsePrimaryKeys(targetClass, _returnMeta);
                }
                // 处理索引
                __doParseIndexes(targetClass, _returnMeta);
                // 注册数据实体类
                __entityMetas.put(targetClass, _returnMeta);
            }
        }
        return _returnMeta;
    }

    public static Set<Class<? extends IEntity>> getEntityClasses() {
        return Collections.unmodifiableSet(__entityMetas.keySet());
    }

    /**
     * 处理字段名称，使其符合JavaBean属性串格式<br>
     * 例如：属性名称为"user_name"，其处理结果为"UserName"<br>
     *
     * @param propertyName 属性名称
     * @return 符合JavaBean属性格式串
     */
    public static String propertyNameToFieldName(String propertyName) {
        if (StringUtils.contains(propertyName, '_')) {
            String[] _words = StringUtils.split(propertyName, '_');
            if (_words != null) {
                if (_words.length > 1) {
                    StringBuilder _returnBuilder = new StringBuilder();
                    for (String _word : _words) {
                        _returnBuilder.append(StringUtils.capitalize(_word.toLowerCase()));
                    }
                    return _returnBuilder.toString();
                }
                return StringUtils.capitalize(_words[0].toLowerCase());
            }
        }
        return propertyName;
    }

    /**
     * 将JavaBean属性串格式转换为下划线小写方式<br>
     * 例如：字段名称为"userName"，其处理结果为"user_name"<br>
     *
     * @param fieldName  字段名称
     * @param capitalize 大小写字母输出方式(小于等于0-全小写，等于1-首字母大写，大于1-全大写)
     * @return 转换以下划线间隔的字符串
     */
    public static String fieldNameToPropertyName(String fieldName, int capitalize) {
        if (StringUtils.isNotBlank(fieldName) && !StringUtils.contains(fieldName, '_')) {
            String _currStr = fieldName.substring(0, 1);
            _currStr = capitalize <= 0 ? _currStr.toLowerCase() : _currStr.toUpperCase();
            StringBuilder _returnBuilder = new StringBuilder(_currStr);
            for (int _idx = 1; _idx < fieldName.length(); _idx++) {
                _currStr = fieldName.substring(_idx, _idx + 1);
                if (_currStr.equals(_currStr.toUpperCase()) && !Character.isDigit(_currStr.charAt(0))) {
                    _returnBuilder.append("_");
                    _currStr = capitalize > 0 ? _currStr.toUpperCase() : _currStr.toLowerCase();
                } else {
                    _currStr = capitalize > 1 ? _currStr.toUpperCase() : _currStr.toLowerCase();
                }
                _returnBuilder.append(_currStr);
            }
            return _returnBuilder.toString();
        }
        return fieldName;
    }

    /**
     * 处理@Property注解
     *
     * @param targetClass 目标类型
     * @param targetMeta  实体元数据
     */
    private static void __doParseProperties(Class<? extends IEntity> targetClass, EntityMeta targetMeta) {
        for (Field _field : ClassUtils.getFields(targetClass, true)) {
            if (ClassUtils.isAnnotationOf(_field, Property.class)) {
                PropertyMeta _meta = __doGetPropertyMeta(_field.getAnnotation(Property.class), _field, targetMeta);
                if (_meta != null) {
                    targetMeta.__properties.put(_meta.getName(), _meta);
                    targetMeta.__fields.put(_meta.getField().getName(), _meta);
                }
            }
        }
    }

    private static PropertyMeta __doGetPropertyMeta(Property property, Field field, EntityMeta targetMeta) {
        PropertyMeta _meta = null;
        // 忽略属性名称已存在的Field对象
        String _propName = StringUtils.defaultIfBlank(property.name(), fieldNameToPropertyName(field.getName(), 0));
        if (!targetMeta.containsProperty(_propName)) {
            field.setAccessible(true);
            _meta = new PropertyMeta(_propName, field, property.autoincrement(),
                    property.sequenceName(), property.nullable(), property.unsigned(), property.length(), property.decimals(), property.type());
            if (ClassUtils.isAnnotationOf(field, Default.class)) {
                _meta.setDefaultValue(field.getAnnotation(Default.class).value());
            }
            if (ClassUtils.isAnnotationOf(field, Comment.class)) {
                _meta.setComment(field.getAnnotation(Comment.class).value());
            }
            if (ClassUtils.isAnnotationOf(field, Readonly.class)) {
                _meta.setReadonly(true);
                targetMeta.__readonlyProps.add(_meta.getName());
            }
            if (_meta.isAutoincrement()) {
                targetMeta.__autoincrementProps.add(_meta.getName());
            }
        }
        return _meta;
    }

    /**
     * 处理@Id注解
     *
     * @param targetClass 目标类型
     * @param targetMeta  实体元数据
     */
    private static void __doParsePrimaryKeys(Class<? extends IEntity> targetClass, EntityMeta targetMeta) {
        PairObject<Field, Id> _id = ClassUtils.getFieldAnnotationFirst(targetClass, Id.class);
        if (_id == null) {
            throw new IllegalArgumentException("Primary key annotation '@Id' not found.");
        }
        // 首选设置主键类型
        targetMeta.__primaryKeyClass = _id.getKey().getType();
        // 判断是否为复合型主键
        if (ClassUtils.isAnnotationOf(_id.getKey().getType(), PK.class)) {
            if (ClassUtils.isInterfaceOf(_id.getKey().getType(), IEntityPK.class)) {
                targetMeta.__multiplePrimaryKey = true;
                //
                for (Field _field : ClassUtils.getFields(_id.getKey().getType(), true)) {
                    if (ClassUtils.isAnnotationOf(_field, Property.class)) {
                        PropertyMeta _meta = __doGetPropertyMeta(_field.getAnnotation(Property.class), _field, targetMeta);
                        if (_meta != null) {
                            targetMeta.__properties.put(_meta.getName(), _meta);
                            targetMeta.__fields.put(_meta.getField().getName(), _meta);
                            //
                            targetMeta.__primaryKeys.add(_meta.getName());
                        }
                    }
                }
            } else {
                throw new IllegalArgumentException("PrimaryKey must implement interface IEntityPK");
            }
        } else {
            targetMeta.__primaryKeys.add(targetMeta.__fields.get(_id.getKey().getName()).getName());
        }
    }

    /**
     * 处理@Indexes和@Index注解
     *
     * @param targetClass 目标类型
     * @param targetMeta  实体元数据
     */
    private static void __doParseIndexes(Class<? extends IEntity> targetClass, EntityMeta targetMeta) {
        List<Index> _indexes = new ArrayList<Index>();
        if (ClassUtils.isAnnotationOf(targetClass, Indexes.class)) {
            _indexes.addAll(Arrays.asList(targetClass.getAnnotation(Indexes.class).value()));
        }
        if (ClassUtils.isAnnotationOf(targetClass, Index.class)) {
            _indexes.add(targetClass.getAnnotation(Index.class));
        }
        for (Index _index : _indexes) {
            // 索引名称和索引字段缺一不可
            if (StringUtils.isNotBlank(_index.name()) && ArrayUtils.isNotEmpty(_index.fields())) {
                // 索引名称不允许重复
                if (!targetMeta.containsIndex(_index.name())) {
                    // 每个字段名称都必须是有效的
                    for (String _field : _index.fields()) {
                        if (!targetMeta.containsProperty(_field)) {
                            throw new IllegalArgumentException("Invalid index field '" + _field + "'");
                        }
                    }
                    targetMeta.__indexes.put(_index.name(), new IndexMeta(_index.name(), _index.unique(), Arrays.asList(_index.fields())));
                }
            }
        }
    }

    /**
     * 私有构造方法
     *
     * @param name         实体名称
     * @param view         是否为视图
     * @param shardingRule 据分片(表)规则
     */
    private EntityMeta(String name, boolean view, ShardingRule shardingRule) {
        this.__primaryKeys = new ArrayList<String>();
        this.__autoincrementProps = new ArrayList<String>();
        this.__readonlyProps = new ArrayList<String>();
        this.__properties = new HashMap<String, PropertyMeta>();
        this.__fields = new HashMap<String, PropertyMeta>();
        this.__indexes = new HashMap<String, IndexMeta>();
        //
        this.__entityName = name;
        this.__view = view;
        this.__shardingRuleAnno = shardingRule;
    }

    /**
     * @param indexName 索引名称
     * @return 返回索引名称是否存在
     */
    public boolean containsIndex(String indexName) {
        return this.__indexes.containsKey(indexName);
    }

    /**
     * @param propertyName 字段名称
     * @return 返回字段名称是否存在
     */
    public boolean containsProperty(String propertyName) {
        return this.__properties.containsKey(propertyName);
    }

    /**
     * @param fieldName 属性名称
     * @return 返回属性名称是否存在
     */
    public boolean containsField(String fieldName) {
        return this.__fields.containsKey(fieldName);
    }

    /**
     * @return 返回是否存在自增长主键
     */
    public boolean hasAutoincrement() {
        return !this.__autoincrementProps.isEmpty();
    }

    /**
     * @param propertyName 字段名称
     * @return 返回字段是否为自增长字段
     */
    public boolean isAutoincrement(String propertyName) {
        return this.__autoincrementProps.contains(propertyName);
    }

    /**
     * @param propertyName 字段名称
     * @return 返回字段是否为主键
     */
    public boolean isPrimaryKey(String propertyName) {
        return this.__primaryKeys.contains(propertyName);
    }

    /**
     * @return 是否为复合主键
     */
    public boolean isMultiplePrimaryKey() {
        return this.__multiplePrimaryKey;
    }

    /**
     * @param propertyName 字段名称
     * @return 返回字段是否为只读
     */
    public boolean isReadonly(String propertyName) {
        return this.__readonlyProps.contains(propertyName);
    }

    /**
     * @return 返回当前实体是否为视图
     */
    public boolean isView() {
        return __view;
    }

    /**
     * @return 返回所有自增长字段名称集合
     */
    public List<String> getAutoincrementKeys() {
        return Collections.unmodifiableList(this.__autoincrementProps);
    }

    /**
     * @return 返回实体名称
     */
    public String getEntityName() {
        return this.__entityName;
    }

    /**
     * @return 返回实体数据分片(表)规则注解对象
     */
    public ShardingRule getShardingRule() {
        return __shardingRuleAnno;
    }

    /**
     * @return 返回主键类型
     */
    public Class<?> getPrimaryKeyClass() {
        return this.__primaryKeyClass;
    }

    /**
     * @return 返回主键字段名称集合
     */
    public List<String> getPrimaryKeys() {
        return Collections.unmodifiableList(this.__primaryKeys);
    }

    /**
     * @return 返回实体属性描述对象集合
     */
    public Collection<PropertyMeta> getProperties() {
        return Collections.unmodifiableCollection(this.__properties.values());
    }

    public Collection<String> getPropertyNames() {
        return Collections.unmodifiableCollection(this.__properties.keySet());
    }

    public PropertyMeta getPropertyByName(String propertyName) {
        return this.__properties.get(propertyName);
    }

    public PropertyMeta getPropertyByField(String fieldName) {
        return this.__fields.get(fieldName);
    }

    /**
     * @return 返回实体索引描述对象集合
     */
    public Collection<IndexMeta> getIndexes() {
        return Collections.unmodifiableCollection(this.__indexes.values());
    }

    /**
     * @return 返回实体注释说明信息
     */
    public String getComment() {
        return this.__comment;
    }

    @Override
    public String toString() {
        return "EntityMeta{" +
                "entityName='" + __entityName + '\'' +
                ", primaryKeyClass=" + __primaryKeyClass +
                ", primaryKeys=" + __primaryKeys +
                ", autoincrementProps=" + __autoincrementProps +
                ", readonlyProps=" + __readonlyProps +
                ", properties=" + __properties +
                ", fields=" + __fields +
                ", indexes=" + __indexes +
                ", multiplePrimaryKey=" + __multiplePrimaryKey +
                ", comment='" + __comment + '\'' +
                '}';
    }

    /**
     * 字段属性描述对象
     *
     * @author 刘镇 (suninformation@163.com) on 15/4/20 上午10:47
     * @version 1.0
     */
    public static class PropertyMeta {
        // 属性名称
        private String name;
        // 成员变量Field对象
        private Field field;
        // 是否为自动增长
        private boolean autoincrement;
        // 序列名称
        private String sequenceName;
        // 允许为空
        private boolean nullable;
        // 是否为无符号
        private boolean unsigned;
        // 数据长度，默认0为不限制
        private int length;
        // 小数位数，默认0为无小数
        private int decimals;
        // 数据类型
        private Type.FIELD type;
        // 默认值
        private String defaultValue;
        // 属性注释
        private String comment;
        // 是否为只读属性
        private boolean readonly;

        public PropertyMeta(String name, Field field) {
            this.name = name;
            this.field = field;
        }

        public PropertyMeta(String name,
                            Field field,
                            boolean autoincrement,
                            String sequenceName,
                            boolean nullable,
                            boolean unsigned,
                            int length,
                            int decimals,
                            Type.FIELD type) {
            this.name = name;
            this.field = field;
            this.autoincrement = autoincrement;
            this.sequenceName = sequenceName;
            this.nullable = nullable;
            this.unsigned = unsigned;
            this.length = length;
            this.decimals = decimals;
            this.type = type;
        }

        public PropertyMeta(String name,
                            Field field,
                            boolean autoincrement,
                            String sequenceName,
                            boolean nullable,
                            boolean unsigned,
                            int length,
                            int decimals,
                            Type.FIELD type,
                            String defaultValue,
                            String comment,
                            boolean readonly) {
            this.name = name;
            this.field = field;
            this.autoincrement = autoincrement;
            this.sequenceName = sequenceName;
            this.nullable = nullable;
            this.unsigned = unsigned;
            this.length = length;
            this.decimals = decimals;
            this.type = type;
            this.defaultValue = defaultValue;
            this.comment = comment;
            this.readonly = readonly;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Field getField() {
            return field;
        }

        public void setField(Field field) {
            this.field = field;
        }

        public boolean isAutoincrement() {
            return autoincrement;
        }

        public void setAutoincrement(boolean autoincrement) {
            this.autoincrement = autoincrement;
        }

        public String getSequenceName() {
            return sequenceName;
        }

        public void setSequenceName(String sequenceName) {
            this.sequenceName = sequenceName;
        }

        public boolean isNullable() {
            return nullable;
        }

        public void setNullable(boolean nullable) {
            this.nullable = nullable;
        }

        public boolean isUnsigned() {
            return unsigned;
        }

        public void setUnsigned(boolean unsigned) {
            this.unsigned = unsigned;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public int getDecimals() {
            return decimals;
        }

        public void setDecimals(int decimals) {
            this.decimals = decimals;
        }

        public Type.FIELD getType() {
            return type;
        }

        public void setType(Type.FIELD type) {
            this.type = type;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public boolean isReadonly() {
            return readonly;
        }

        public void setReadonly(boolean readonly) {
            this.readonly = readonly;
        }

        @Override
        public String toString() {
            return "PropertyMeta{" +
                    "name='" + name + '\'' +
                    ", field=" + field +
                    ", autoincrement=" + autoincrement +
                    ", sequenceName='" + sequenceName + '\'' +
                    ", nullable=" + nullable +
                    ", unsigned=" + unsigned +
                    ", length=" + length +
                    ", decimals=" + decimals +
                    ", type=" + type +
                    ", defaultValue='" + defaultValue + '\'' +
                    ", comment='" + comment + '\'' +
                    ", readonly=" + readonly +
                    '}';
        }
    }

    /**
     * 索引属性描述对象
     *
     * @author 刘镇 (suninformation@163.com) on 15/4/20 下午18:25
     * @version 1.0
     */
    public static class IndexMeta {
        // 索引名称
        private String name;
        // 是否唯一索引
        private boolean unique;
        // 索引字段名称集合
        private List<String> fields;

        public IndexMeta(String name, boolean unique, List<String> fields) {
            this.name = name;
            this.unique = unique;
            this.fields = fields;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isUnique() {
            return unique;
        }

        public void setUnique(boolean unique) {
            this.unique = unique;
        }

        public List<String> getFields() {
            return fields;
        }

        public void setFields(List<String> fields) {
            this.fields = fields;
        }

        @Override
        public String toString() {
            return "IndexMeta{" +
                    "name='" + name + '\'' +
                    ", unique=" + unique +
                    ", fields=" + fields +
                    '}';
        }
    }
}
