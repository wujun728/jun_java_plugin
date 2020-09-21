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
package net.ymate.platform.core.lang;

import net.ymate.platform.core.util.RuntimeUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.util.*;

/**
 * 模糊对象，任意数据类型间转换
 *
 * @author 刘镇 (suninformation@163.com) on 2010-4-16 下午11:51:39
 * @version 1.0
 */
public class BlurObject implements Serializable, Cloneable {

    private static final Log _LOG = LogFactory.getLog(BlurObject.class);

    /**
     *
     */
    private static final long serialVersionUID = 4141840934670622411L;

    /**
     * 当前存储对象值
     */
    private Object attr;

    public static BlurObject bind(Object o) {
        return new BlurObject(o);
    }

    public BlurObject(Object o) {
        attr = o;
    }

    /**
     * @return 输出对象
     */
    public Object toObjectValue() {
        return attr;
    }

    /**
     * @return 输出模糊对象
     */
    public BlurObject toBlurObjectValue() {
        if (attr instanceof BlurObject) {
            return (BlurObject) attr;
        }
        return this;
    }

    /**
     * @return 输出为映射
     */
    public Map<?, ?> toMapValue() {
        if (attr == null) {
            return null;
        }
        if (attr instanceof Map) {
            return (Map<?, ?>) attr;
        }
        return Collections.emptyMap();
    }

    /**
     * @return 输出为列表
     */
    public List<?> toListValue() {
        if (attr == null) {
            return null;
        }
        if (attr instanceof List) {
            return (List<? extends Object>) attr;
        }
        List<Object> _returnValue = new LinkedList<Object>();
        _returnValue.add(attr);
        return _returnValue;
    }

    /**
     * @return 输出为集合
     */
    public Set<?> toSetValue() {
        if (attr == null) {
            return null;
        }
        if (attr instanceof List) {
            return (Set<? extends Object>) attr;
        }
        Set<Object> _returnValue = new LinkedHashSet<Object>();
        _returnValue.add(attr);
        return _returnValue;
    }

    /**
     * @return 输出布尔值，如果当前类型非布尔值，那么尝试转换
     */
    public boolean toBooleanValue() {
        if (attr == null) {
            return false;
        }
        if (attr instanceof String) {
            return "true".equalsIgnoreCase(this.attr.toString()) || "on".equalsIgnoreCase(this.attr.toString()) || "1".equalsIgnoreCase(this.attr.toString());
        }
        if (attr instanceof Boolean || boolean.class.isAssignableFrom(attr.getClass())) {
            return (Boolean) attr;
        }
        if (float.class.isAssignableFrom(attr.getClass())) {
            return ((Float) attr) > 0;
        }
        if (int.class.isAssignableFrom(attr.getClass())) {
            return ((Integer) attr).floatValue() > 0;
        }
        if (long.class.isAssignableFrom(attr.getClass())) {
            return ((Long) attr).floatValue() > 0;
        }
        if (double.class.isAssignableFrom(attr.getClass())) {
            return ((Double) attr).floatValue() > 0;
        }
        if (attr instanceof Number) {
            return ((Number) attr).floatValue() > 0;
        }
        if (attr instanceof List) {
            return ((List) attr).size() > 0;
        }
        if (attr instanceof Map) {
            return ((Map) attr).size() > 0;
        }
        return attr instanceof BlurObject && ((BlurObject) this.attr).toBooleanValue();
    }

    /**
     * @return 输出整数
     */
    public int toIntValue() {
        if (attr == null) {
            return 0;
        }
        if (attr instanceof Integer || int.class.isAssignableFrom(attr.getClass())) {
            return (Integer) attr;
        }
        if (attr instanceof String) {
            if (StringUtils.isNotBlank(attr.toString())) {
                return Integer.parseInt(attr.toString(), 10);
            } else {
                return 0;
            }
        }
        if (long.class.isAssignableFrom(attr.getClass())) {
            return ((Long) attr).intValue();
        }
        if (float.class.isAssignableFrom(attr.getClass())) {
            return ((Float) attr).intValue();
        }
        if (double.class.isAssignableFrom(attr.getClass())) {
            return ((Double) attr).intValue();
        }
        if (attr instanceof Number) {
            return ((Number) attr).intValue();
        }
        if (attr instanceof Boolean || boolean.class.isAssignableFrom(attr.getClass())) {
            return (Boolean) attr ? 1 : 0;
        }
        if (attr instanceof Map) {
            return ((Map) attr).size();
        }
        if (attr instanceof List) {
            return ((List) attr).size();
        }
        if (attr instanceof Short || short.class.isAssignableFrom(attr.getClass())) {
            return ((Short) attr).intValue();
        }
        if (attr instanceof BlurObject) {
            return ((BlurObject) this.attr).toIntValue();
        }
        return 0;
    }

    /**
     * @return 输出串
     */
    public String toStringValue() {
        if (attr == null) {
            return null;
        }
        if (attr instanceof String) {
            return (String) attr;
        }
        if (attr instanceof Clob) {
            Clob _clob = (Clob) attr;
            Reader _reader = null;
            try {
                _reader = _clob.getCharacterStream();
                if (_clob.length() > 0 && _reader != null) {
                    return IOUtils.toString(_reader);
                }
            } catch (Exception e) {
                _LOG.warn("", RuntimeUtils.unwrapThrow(e));
            } finally {
                IOUtils.closeQuietly(_reader);
            }
        }
        if (attr instanceof BlurObject) {
            return ((BlurObject) this.attr).toStringValue();
        }
        return attr.toString();
    }

    /**
     * @return 输出浮点数
     */
    public float toFloatValue() {
        if (attr == null) {
            return 0f;
        }
        if (float.class.isAssignableFrom(attr.getClass())) {
            return (Float) attr;
        }
        if (int.class.isAssignableFrom(attr.getClass())) {
            return ((Integer) attr).floatValue();
        }
        if (long.class.isAssignableFrom(attr.getClass())) {
            return ((Long) attr).floatValue();
        }
        if (double.class.isAssignableFrom(attr.getClass())) {
            return ((Double) attr).floatValue();
        }
        if (attr instanceof Number) {
            return ((Number) attr).floatValue();
        }
        if (attr instanceof String) {
            if (StringUtils.isNotBlank(attr.toString())) {
                return Float.parseFloat(attr.toString());
            } else {
                return 0f;
            }
        }
        if (attr instanceof Boolean || boolean.class.isAssignableFrom(attr.getClass())) {
            return (Boolean) attr ? 1f : 0f;
        }
        if (attr instanceof Map) {
            return ((Map) attr).size();
        }
        if (attr instanceof List) {
            return ((List) attr).size();
        }
        if (attr instanceof BlurObject) {
            return ((BlurObject) this.attr).toFloatValue();
        }
        return 0f;
    }

    /**
     * @return 输出双精度
     */
    public double toDoubleValue() {
        if (attr == null) {
            return 0d;
        }
        if (double.class.isAssignableFrom(attr.getClass())) {
            return (Double) attr;
        }
        if (int.class.isAssignableFrom(attr.getClass())) {
            return ((Integer) attr).doubleValue();
        }
        if (long.class.isAssignableFrom(attr.getClass())) {
            return ((Long) attr).doubleValue();
        }
        if (float.class.isAssignableFrom(attr.getClass())) {
            return ((Float) attr).doubleValue();
        }
        if (attr instanceof Number) {
            return ((Number) attr).doubleValue();
        }
        if (attr instanceof String) {
            if (StringUtils.isNotBlank(attr.toString())) {
                return Double.parseDouble(attr.toString());
            } else {
                return 0d;
            }
        }
        if (attr instanceof Boolean || boolean.class.isAssignableFrom(attr.getClass())) {
            return (Boolean) attr ? 1d : 0d;
        }
        if (attr instanceof Map) {
            return ((Map) attr).size();
        }
        if (attr instanceof List) {
            return ((List) attr).size();
        }
        if (attr instanceof BlurObject) {
            return ((BlurObject) this.attr).toDoubleValue();
        }
        return 0d;
    }

    /**
     * @return 输出长整形
     */
    public long toLongValue() {
        if (attr == null) {
            return 0;
        }
        if (long.class.isAssignableFrom(attr.getClass())) {
            return (Long) attr;
        }
        if (int.class.isAssignableFrom(attr.getClass())) {
            return ((Integer) attr).longValue();
        }
        if (float.class.isAssignableFrom(attr.getClass())) {
            return ((Float) attr).longValue();
        }
        if (double.class.isAssignableFrom(attr.getClass())) {
            return ((Double) attr).longValue();
        }
        if (attr instanceof Number) {
            return ((Number) attr).longValue();
        }
        if (attr instanceof String) {
            if (StringUtils.isNotBlank(attr.toString())) {
                return Long.parseLong(attr.toString(), 10);
            } else {
                return 0;
            }
        }
        if (attr instanceof Boolean || boolean.class.isAssignableFrom(attr.getClass())) {
            return (Boolean) attr ? 1 : 0;
        }
        if (attr instanceof Map) {
            return ((Map) attr).size();
        }
        if (attr instanceof List) {
            return ((List) attr).size();
        }
        if (attr instanceof BlurObject) {
            return ((BlurObject) this.attr).toLongValue();
        }
        return 0;
    }

    public byte toByteValue() {
        if (attr == null) {
            return 0;
        }
        if (attr instanceof Byte) {
            return (Byte) attr;
        }
        if (attr instanceof BlurObject) {
            return ((BlurObject) this.attr).toByteValue();
        }
        return Byte.parseByte(toStringValue());
    }

    public byte[] toBytesValue() {
        if (attr instanceof byte[]) {
            return (byte[]) attr;
        }
        if (attr instanceof Byte[]) {
            Byte[] _bArr = (Byte[]) attr;
            byte[] _returnBArr = new byte[_bArr.length];
            for (int _idx = 0; _idx < _bArr.length; _idx++) {
                _returnBArr[_idx] = _bArr[_idx];
            }
            return _returnBArr;
        }
        if (attr instanceof Blob) {
            Blob _blob = ((Blob) attr);
            InputStream _input = null;
            try {
                _input = _blob.getBinaryStream();
                if (_blob.length() > 0 && _input != null) {
                    byte[] _bArr = new byte[(int) _blob.length()];
                    if (_input.read(_bArr) > 0) {
                        return _bArr;
                    }
                }
            } catch (Exception e) {
                _LOG.warn("", RuntimeUtils.unwrapThrow(e));
            } finally {
                IOUtils.closeQuietly(_input);
            }
        }
        return null;
    }

    public short toShortValue() {
        return (short) toIntValue();
    }

    public char toCharValue() {
        if (attr == null) {
            return Character.MIN_CODE_POINT;
        }
        if (attr instanceof Character) {
            return (Character) attr;
        }
        if (attr instanceof BlurObject) {
            return ((BlurObject) this.attr).toCharValue();
        }
        return Character.MIN_CODE_POINT;
    }

    /**
     * 输出指定类的对象
     *
     * @param clazz 指定类
     * @return 如果对象不能转换成指定类返回null，指定类是null，返回null。
     */
    public Object toObjectValue(Class<?> clazz) {
        Object object = null;
        if (clazz.equals(String.class)) {
            object = attr == null ? null : this.toStringValue();
        } else if (clazz.equals(Double.class)) {
            object = attr == null ? null : this.toDoubleValue();
        } else if (clazz.equals(double.class)) {
            object = this.toDoubleValue();
        } else if (clazz.equals(Float.class)) {
            object = attr == null ? null : this.toFloatValue();
        } else if (clazz.equals(float.class)) {
            object = this.toFloatValue();
        } else if (clazz.equals(Integer.class)) {
            object = attr == null ? null : this.toIntValue();
        } else if (clazz.equals(int.class)) {
            object = this.toIntValue();
        } else if (clazz.equals(Long.class)) {
            object = attr == null ? null : this.toLongValue();
        } else if (clazz.equals(long.class)) {
            object = this.toLongValue();
        } else if (clazz.equals(BigInteger.class)) {
            String _value = StringUtils.trimToNull(toStringValue());
            if (_value != null) {
                object = new BigInteger(_value);
            }
        } else if (clazz.equals(BigDecimal.class)) {
            String _value = StringUtils.trimToNull(toStringValue());
            if (_value != null) {
                object = new BigDecimal(_value);
            }
        } else if (clazz.equals(Boolean.class)) {
            object = attr == null ? null : this.toBooleanValue();
        } else if (clazz.equals(boolean.class)) {
            object = this.toBooleanValue();
        } else if (clazz.equals(Byte.class)) {
            object = attr == null ? null : this.toByteValue();
        } else if (clazz.equals(byte.class)) {
            object = this.toByteValue();
        } else if (clazz.equals(Byte[].class)) {
            object = attr == null ? null : this.toBytesValue();
        } else if (clazz.equals(byte[].class)) {
            object = this.toBytesValue();
        } else if (clazz.equals(Character.class)) {
            object = attr == null ? null : this.toCharValue();
        } else if (clazz.equals(char.class)) {
            object = this.toCharValue();
        } else if (clazz.equals(List.class)) {
            object = this.toListValue();
        } else if (clazz.equals(Map.class)) {
            object = this.toMapValue();
        } else if (clazz.equals(Set.class)) {
            object = this.toSetValue();
        }
        if (object == null) {
            try {
                object = clazz.cast(attr);
            } catch (ClassCastException e) {
                _LOG.warn("", RuntimeUtils.unwrapThrow(e));
            }
        }
        return object;
    }

    /**
     * @return 获得对象类
     */
    public Class<?> getObjectClass() {
        if (attr != null) {
            return attr.getClass();
        }
        return null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((attr == null) ? 0 : attr.hashCode());
        Class<?> _attrClass = attr == null ? null : attr.getClass();
        result = prime * result + ((_attrClass == null) ? 0 : _attrClass.hashCode());
        result = prime * result;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BlurObject other = (BlurObject) obj;
        if (this.attr == null) {
            if (other.attr != null) {
                return false;
            }
        } else if (!this.attr.equals(other.attr)) {
            return false;
        }
        Class<?> _attrClass = attr == null ? null : attr.getClass();
        if (_attrClass == null) {
            if ((other.attr != null ? other.attr.getClass() : null) != null) {
                return false;
            }
        } else if (!_attrClass.equals(other.attr.getClass())) {
            return false;
        }
        return attr == other.attr;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        if (attr != null) {
            return attr.toString();
        }
        return "";
    }
}
