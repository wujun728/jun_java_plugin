package org.springrain.frame.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.BigIntegerPoint;
import org.apache.lucene.document.DoublePoint;
import org.apache.lucene.document.FloatPoint;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;

/**
 * Lucene查找工具类
 * 
 * @author caomei
 *
 */
public class LuceneFinder {

    // 搜索的关键字
    private String keyword = null;
    private List<BooleanClause> listCondition = new ArrayList<>();
    private List<String> fieldList = new ArrayList<>();
    private List<SortField> sortFieldList = new ArrayList<>();
    private Sort sort = null;
    private String[] fields = null;

    public LuceneFinder(String keyword) {
        this.keyword = keyword;
    }

    /**
     * 字段值等于某个值
     * 
     * @param fieldName
     * @param value
     * @return
     * @throws Exception
     */
    public List<BooleanClause> addWhereCondition(String fieldName, Class fieldType, Object value) throws Exception {
        return addWhereCondition(fieldName, fieldType, value, Occur.MUST);
    }

    /**
     * 字段值在某个值中间
     * 
     * @param fieldName
     * @param minValue
     * @param maxValue
     * @return
     * @throws Exception
     */
    public List<BooleanClause> addWhereConditionRange(String fieldName, Class fieldType, Object minValue, Object maxValue)
            throws Exception {
        return addWhereConditionRange(fieldName, fieldType, minValue, maxValue, true, true, Occur.MUST);
    }

    /**
     * 添加排序的字段,默认正序
     * 
     * @param fieldName
     * @param fieldType
     * @return
     * @throws Exception
     */
    public List<SortField> addSortField(String fieldName, Class fieldType) throws Exception {
        return addSortField(fieldName, fieldType, false);
    }

    /**
     * 添加排序的字段
     * 
     * @param fieldName
     * @param fieldType
     * @param relevance
     * @return
     * @throws Exception
     */

    public List<SortField> addSortField(String fieldName, Class fieldType, boolean relevance) throws Exception {
        if (StringUtils.isEmpty(fieldName) || fieldType == null) {
            return null;
        }

        // Type type=Type.STRING;//使用SortedDocValuesField也有限制,暂时不支持String排序
        Type type = null;

        if (Integer.class == fieldType || int.class == fieldType) {// 数字
            type = Type.INT;
        } else if (Long.class == fieldType || long.class == fieldType) {// 数字
            type = Type.LONG;
        } else if (Float.class == fieldType || float.class == fieldType) {// 数字
            type = Type.FLOAT;
        } else if (Double.class == fieldType || double.class == fieldType) {// 数字
            type = Type.DOUBLE;
        } else if (Date.class == fieldType) {// 日期
            type = Type.LONG;
        }

        if (type == null) {
            throw new Exception("不支持的排序类型,目前仅支持数值类型的排序");
        }

        SortField sf = new SortField(fieldName, type, relevance);
        sortFieldList.add(sf);

        return sortFieldList;
    }

    /**
     * 字段值等于某个值
     * 
     * @param fieldName
     * @param fieldType
     * @param value
     * @param occur
     * @return
     * @throws Exception
     */
    public List<BooleanClause> addWhereCondition(String fieldName, Class fieldType, Object value, Occur occur)
            throws Exception {

        if (fieldType == null || StringUtils.isEmpty(fieldName) || value == null) {
            return listCondition;
        }

        Query query = null;

        if (Integer.class == fieldType || int.class == fieldType) {// 数字
            query = IntPoint.newExactQuery(fieldName, Integer.valueOf(value.toString()));
        } else if (BigInteger.class == fieldType) {// 数字
            query = BigIntegerPoint.newExactQuery(fieldName, new BigInteger(value.toString()));
        } else if (Long.class == fieldType || long.class == fieldType) {// 数字
            query = LongPoint.newExactQuery(fieldName, Long.valueOf(value.toString()));
        } else if (Float.class == fieldType || float.class == fieldType) {// 数字
            query = FloatPoint.newExactQuery(fieldName, Float.valueOf(value.toString()));
        } else if (Double.class == fieldType || double.class == fieldType) {// 数字
            query = DoublePoint.newExactQuery(fieldName, Double.valueOf(value.toString()));
        } else if (Date.class == fieldType) {// 日期
            query = LongPoint.newExactQuery(fieldName, Long.valueOf(((Date) value).getTime()));
        } else {
            Term term = new Term(fieldName, value.toString());
            query = new TermQuery(term);
        }

        BooleanClause bc = new BooleanClause(query, occur);

        listCondition.add(bc);

        return listCondition;
    }

    /**
     * 字段值在某个值中间
     * 
     * @param fieldName
     * @param fieldType
     * @param minValue
     * @param maxValue
     * @param includeLower
     * @param includeUpper
     * @param occur
     * @return
     * @throws Exception
     */
    public List<BooleanClause> addWhereConditionRange(String fieldName, Class fieldType, Object minValue, Object maxValue,
            boolean includeLower, boolean includeUpper, Occur occur) throws Exception {

        if (fieldType == null || StringUtils.isEmpty(fieldName) || minValue == null || maxValue == null) {
            return listCondition;
        }

        Query query = null;

        if (Integer.class == fieldType || int.class == fieldType) {// 数字
            query = IntPoint.newRangeQuery(fieldName, Integer.valueOf(minValue.toString()),
                    Integer.valueOf(maxValue.toString()));
        } else if (BigInteger.class == fieldType) {// 数字
            query = BigIntegerPoint.newRangeQuery(fieldName, new BigInteger(minValue.toString()),
                    new BigInteger(maxValue.toString()));
        } else if (Long.class == fieldType || long.class == fieldType) {// 数字
            query = LongPoint.newRangeQuery(fieldName, Long.valueOf(minValue.toString()),
                    Long.valueOf(maxValue.toString()));
        } else if (Float.class == fieldType || float.class == fieldType) {// 数字
            query = FloatPoint.newRangeQuery(fieldName, Float.valueOf(minValue.toString()),
                    Float.valueOf(maxValue.toString()));
        } else if (Double.class == fieldType || double.class == fieldType) {// 数字
            query = DoublePoint.newRangeQuery(fieldName, Double.valueOf(minValue.toString()),
                    Double.valueOf(maxValue.toString()));
        } else if (Date.class == fieldType) {// 日期
            query = LongPoint.newRangeQuery(fieldName, Long.valueOf(((Date) minValue).getTime()),
                    Long.valueOf(((Date) maxValue).getTime()));
        } else {
            query = TermRangeQuery.newStringRange(fieldName, minValue.toString(), maxValue.toString(), includeLower,
                    includeUpper);
        }

        BooleanClause bc = new BooleanClause(query, occur);

        listCondition.add(bc);

        return listCondition;
    }

    public String getKeyword() {
        return keyword;
    }

    public List<BooleanClause> getListCondition() {
        return listCondition;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setListCondition(List<BooleanClause> listCondition) {
        this.listCondition = listCondition;
    }

    public List<String> getFieldList() {
        return fieldList;
    }

    public Sort getSort() {
        if (sort == null && CollectionUtils.isNotEmpty(sortFieldList)) {
            sort = new Sort(sortFieldList.toArray(new SortField[sortFieldList.size()]));
        }

        return sort;
    }

    public void setFieldList(List<String> fieldList) {
        this.fieldList = fieldList;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public String[] getFields() {
        if (fields == null && CollectionUtils.isNotEmpty(fieldList)) {
            fields = fieldList.toArray(new String[fieldList.size()]);
        }

        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public List<SortField> getSortFieldList() {
        return sortFieldList;
    }

    public void setSortFieldList(List<SortField> sortFieldList) {
        this.sortFieldList = sortFieldList;
    }

}
