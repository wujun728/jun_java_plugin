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
package net.ymate.platform.persistence.jdbc.support;

import net.ymate.platform.core.lang.BlurObject;
import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.persistence.IResultSet;
import net.ymate.platform.persistence.base.EntityMeta;
import net.ymate.platform.persistence.base.IEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 数据结果集处理类，用于帮助开发人员便捷的读取结果集中数据内容<br>
 * 注：此类仅支持结果集由 ArrayResultSetHandler 和 MapResultSetHandler 产生的数据<br>
 *
 * @author 刘镇 (suninformation@163.com) on 2010-10-10 上午10:59:40
 * @version 1.0
 */
public final class ResultSetHelper {

    /**
     * 数据结果集
     */
    private List<?> __dataSet;

    private boolean __isArray;

    private int __rowCount;

    private int __columnCount;

    private boolean __clearFlag;

    private String __columnNames[];

    public static ResultSetHelper bind(Object[] data) {
        List<Object[]> _data = new ArrayList<Object[]>();
        _data.add(data);
        return bind(_data);
    }

    public static ResultSetHelper bind(Map<String, Object> data) {
        List<Map<String, Object>> _data = new ArrayList<Map<String, Object>>();
        _data.add(data);
        return bind(_data);
    }

    public static ResultSetHelper bind(IResultSet<?> resultSet) {
        return bind(resultSet.getResultData());
    }

    /**
     * @param dataSet 结果数据集合
     * @return 绑定结果集数据，若参数为空则返回 null
     */
    public static ResultSetHelper bind(List<?> dataSet) {
        if (dataSet != null && !dataSet.isEmpty()) {
            Object _value = dataSet.get(0);
            if (_value instanceof Map) {
                return new ResultSetHelper(dataSet, false);
            } else if (_value instanceof Object[]) {
                return new ResultSetHelper(dataSet, true);
            }
        }
        return null;
    }


    /**
     * 构造器
     *
     * @param resultSet 结果数据集合
     * @param isArray   集合中数据是否为数组类型
     */
    @SuppressWarnings("unchecked")
    private ResultSetHelper(List<?> resultSet, boolean isArray) {
        this.__dataSet = resultSet;
        this.__isArray = isArray;
        if (this.__dataSet != null) {
            this.__rowCount = this.__dataSet.size();
            if (this.__rowCount > 0) {
                // 计算字段数量
                if (this.__isArray) {
                    this.__columnCount = ((Object[]) this.__dataSet.get(0)).length;
                } else {
                    this.__columnCount = ((Map<?, ?>) this.__dataSet.get(0)).size();
                }
                // 处理字段名称集合
                if (this.__isArray) {
                    Object[] _obj = (Object[]) this.__dataSet.get(0);
                    this.__columnNames = new String[_obj.length];
                    for (int i = 0; i < _obj.length; i++) {
                        Object[] _columnObj = (Object[]) _obj[i];
                        this.__columnNames[i] = (String) _columnObj[0];
                    }
                } else {
                    Map<String, Object> _map = (Map<String, Object>) this.__dataSet.get(0);
                    Iterator<String> _itemIt = _map.keySet().iterator();
                    this.__columnNames = new String[_map.keySet().size()];
                    int _idx = 0;
                    while (_itemIt.hasNext()) {
                        this.__columnNames[_idx] = _itemIt.next();
                        _idx++;
                    }
                }
            }
        }
    }

    /**
     * 获取结果集的列名
     *
     * @return String[] 字段名称集合
     */
    public String[] getColumnNames() {
        return this.__columnNames;
    }

    /**
     * 清除结果集 本方法为可选方法
     */
    public void clearAll() {
        if (this.__dataSet != null) {
            this.__dataSet.clear();
            this.__dataSet = null;
        }
        this.__columnNames = null;
        this.__clearFlag = true;
    }

    @Override
    protected void finalize() throws Throwable {
        if (!this.__clearFlag) {
            clearAll();
        }
        super.finalize();
    }

    /**
     * 遍历结果集合
     *
     * @param handler 结果集元素处理器
     * @throws Exception 可能产生的异常
     */
    public void forEach(ItemHandler handler) throws Exception {
        this.forEach(1, handler);
    }

    /**
     * 遍历结果集合
     *
     * @param step    步长
     * @param handler 结果集元素处理器
     * @throws Exception 可能产生的异常
     */
    public void forEach(int step, ItemHandler handler) throws Exception {
        step = (step > 0 ? step : 1);
        for (int _idx = step - 1; _idx < __rowCount; _idx += step) {
            if (!handler.handle(new ItemWrapper(__dataSet.get(_idx), __isArray), _idx)) {
                break;
            }
        }
    }

    /**
     * @return 返回结果集中第一个元素的包装对象
     */
    public ItemWrapper firstItemWrapper() {
        return new ItemWrapper(__dataSet.get(0), __isArray);
    }

    /**
     * 结果集元素处理器
     */
    public interface ItemHandler {

        /**
         * @param wrapper 元素包装对象，提供多种数据提取方法
         * @param row     结果集当前所在行数
         * @return 返回值将决定此次遍历是否继续执行，true或false
         * @throws Exception 可能产生的异常
         */
        boolean handle(ItemWrapper wrapper, int row) throws Exception;
    }

    /**
     * 结果集元素包装对象
     */
    public class ItemWrapper {

        private Object __item;

        private boolean __isArray;

        public ItemWrapper(Object item, boolean isArray) {
            __item = item;
            __isArray = isArray;
        }

        public int getColumnCount() {
            return __columnCount;
        }

        public String[] getColumnNames() {
            return __columnNames;
        }

        /**
         * @param columnName 字段名称
         * @return 按照字段名获取字段值
         */
        public Object getObject(String columnName) {
            return this.__doGetObject(columnName);
        }

        @SuppressWarnings("unchecked")
        private Object __doGetObject(String columnName) {
            Object _returnValue = null;
            if (this.__isArray) {
                Object[] _obj = (Object[]) __item;
                for (int i = 0; i < __columnNames.length; i++) {
                    if (__columnNames[i].equalsIgnoreCase(columnName)) {
                        Object[] _object = (Object[]) _obj[i];
                        _returnValue = _object[1];
                        break;
                    }
                }
            } else {
                Map<String, Object> _map = (Map<String, Object>) __item;
                _returnValue = _map.get(columnName);
                if (_returnValue == null) {
                    for (String __columnName : __columnNames) {
                        if (__columnName.equalsIgnoreCase(columnName)) {
                            _returnValue = _map.get(__columnName);
                            break;
                        }
                    }
                }
            }
            return _returnValue;
        }

        /**
         * @param index 索引
         * @return 按列名顺序获取字段值
         */
        public Object getObject(int index) {
            return this.__doGetObject(index);
        }

        @SuppressWarnings("unchecked")
        private Object __doGetObject(int index) {
            Object _returnValue = null;
            if (index >= 0 && index < __columnCount) {
                if (this.__isArray) {
                    Object[] _obj = (Object[]) __item;
                    Object[] _object = (Object[]) _obj[index];
                    _returnValue = _object[1];
                } else {
                    Map<String, Object> _map = (Map<String, Object>) __item;
                    Iterator<Object> _itemIt = _map.values().iterator();
                    int i = 0;
                    while (_itemIt.hasNext()) {
                        _returnValue = _itemIt.next();
                        if (index == i) {
                            break;
                        } else {
                            _returnValue = null;
                        }
                        i++;
                    }
                }
            }
            return _returnValue;
        }

        public Time getAsTime(int i) {
            Object o = getObject(i);
            if (o == null) {
                return null;
            }
            if (o instanceof Time) {
                return (Time) o;
            } else {
                return new Time(((Date) o).getTime());
            }
        }

        public Time getAsTime(String columnName) {
            Object o = getObject(columnName);
            if (o == null) {
                return null;
            }
            if (o instanceof Time) {
                return (Time) o;
            }
            return new Time(((Date) o).getTime());
        }

        public Timestamp getAsTimestamp(int i) {
            Object o = getObject(i);
            if (o == null) {
                return null;
            }
            if (o instanceof Timestamp) {
                return (Timestamp) o;
            }
            return new Timestamp(((Date) o).getTime());
        }

        public Timestamp getAsTimestamp(String columnName) {
            Object o = getObject(columnName);
            if (o == null) {
                return null;
            }
            if (o instanceof Timestamp) {
                return (Timestamp) o;
            }
            return new Timestamp(((Date) o).getTime());
        }

        public Date getAsDate(int i) {
            Object o = getObject(i);
            if (o == null) {
                return null;
            }
            if (o instanceof Date) {
                return (Date) o;
            }
            return new Date(((Timestamp) o).getTime());
        }

        public Date getAsDate(String columnName) {
            Object o = getObject(columnName);
            if (o == null) {
                return null;
            }
            if (o instanceof Date) {
                return (Date) o;
            }
            return new Date(((Timestamp) o).getTime());
        }

        public Float getAsFloat(int i) {
            Object o = getObject(i);
            if (o == null) {
                return null;
            }
            return BlurObject.bind(o).toFloatValue();
        }

        public Float getAsFloat(String columnName) {
            Object o = getObject(columnName);
            if (o == null) {
                return null;
            }
            return BlurObject.bind(o).toFloatValue();
        }

        public Double getAsDouble(int i) {
            Object o = getObject(i);
            if (o == null) {
                return null;
            }
            return BlurObject.bind(o).toDoubleValue();
        }

        public Double getAsDouble(String columnName) {
            Object o = getObject(columnName);
            if (o == null) {
                return null;
            }
            return BlurObject.bind(o).toDoubleValue();
        }

        public Byte getAsByte(int i) {
            Object o = getObject(i);
            if (o == null) {
                return null;
            }
            if (o instanceof Byte) {
                return (Byte) o;
            } else if (o instanceof Integer) {
                return ((Integer) o).byteValue();
            } else {
                return ((BigDecimal) o).byteValue();
            }
        }

        public Byte getAsByte(String columnName) {
            Object o = getObject(columnName);
            if (o == null) {
                return null;
            }
            if (o instanceof Byte) {
                return (Byte) o;
            } else if (o instanceof Integer) {
                return ((Integer) o).byteValue();
            } else {
                return ((BigDecimal) o).byteValue();
            }
        }

        public Short getAsShort(int i) {
            Object o = getObject(i);
            if (o == null) {
                return null;
            }
            if (o instanceof Short) {
                return (Short) o;
            } else if (o instanceof Integer) {
                return ((Integer) o).shortValue();
            } else {
                return ((BigDecimal) o).shortValue();
            }
        }

        public Short getAsShort(String columnName) {
            Object o = getObject(columnName);
            if (o == null) {
                return null;
            }
            if (o instanceof Short) {
                return (Short) o;
            } else if (o instanceof Integer) {
                return ((Integer) o).shortValue();
            } else {
                return ((BigDecimal) o).shortValue();
            }
        }

        public Long getAsLong(int i) {
            Object o = getObject(i);
            if (o == null) {
                return null;
            }
            return BlurObject.bind(o).toLongValue();
        }

        public Long getAsLong(String columnName) {
            Object o = getObject(columnName);
            if (o == null) {
                return null;
            }
            return BlurObject.bind(o).toLongValue();
        }

        public BigDecimal getAsBigDecimal(int i) {
            Object o = getObject(i);
            if (o == null) {
                return null;
            }
            return (BigDecimal) o;
        }

        public BigDecimal getAsBigDecimal(String columnName) {
            Object o = getObject(columnName);
            if (o == null) {
                return null;
            }
            return (BigDecimal) o;
        }

        public Integer getAsInteger(int i) {
            Object o = getObject(i);
            if (o == null) {
                return null;
            }
            return BlurObject.bind(o).toIntValue();
        }

        public Integer getAsInteger(String columnName) {
            Object o = getObject(columnName);
            if (o == null) {
                return null;
            }
            return new BlurObject(o).toIntValue();
        }

        public Character getAsChar(String columnName) {
            Object o = getObject(columnName);
            if (o == null) {
                return null;
            }
            if (o instanceof Character) {
                return (Character) o;
            } else {
                return o.toString().charAt(0);
            }
        }

        public Character getAsChar(int index) {
            Object o = getObject(index);
            if (o == null) {
                return null;
            }
            if (o instanceof Character) {
                return (Character) o;
            } else {
                return o.toString().charAt(0);
            }
        }

        public String getAsString(String columnName) {
            Object v = getObject(columnName);
            if (v != null) {
                return v.toString();
            } else {
                return null;
            }
        }

        public String getAsString(int index) {
            Object v = getObject(index);
            if (v != null) {
                return v.toString();
            } else {
                return null;
            }
        }

        @SuppressWarnings("unchecked")
        public <T extends IEntity> T toEntity(T entityObject) throws Exception {
            EntityMeta _entityMeta = EntityMeta.createAndGet(entityObject.getClass());
            Object _primaryKeyObject = null;
            if (_entityMeta.isMultiplePrimaryKey()) {
                _primaryKeyObject = _entityMeta.getPrimaryKeyClass().newInstance();
                //
                entityObject.setId((Serializable) _primaryKeyObject);
            }
            for (EntityMeta.PropertyMeta _meta : _entityMeta.getProperties()) {
                Object _fValue = getObject(_meta.getName());
                if (_fValue != null) {
                    if (_entityMeta.isPrimaryKey(_meta.getName()) && _entityMeta.isMultiplePrimaryKey()) {
                        _meta.getField().set(_primaryKeyObject, _fValue);
                    } else {
                        _meta.getField().set(entityObject, _fValue);
                    }
                }
            }
            return entityObject;
        }

        public <T> T toObject(T valueObject) throws Exception {
            ClassUtils.BeanWrapper<?> _wrapper = ClassUtils.wrapper(valueObject);
            for (String _fieldName : _wrapper.getFieldNames()) {
                String _columnName = EntityMeta.fieldNameToPropertyName(_fieldName, 0);
                Object _value = this.getObject(_columnName);
                if (_value == null) {
                    continue;
                }
                _wrapper.setValue(_fieldName, BlurObject.bind(_value).toObjectValue(_wrapper.getFieldType(_fieldName)));
            }
            return valueObject;
        }
    }
}
