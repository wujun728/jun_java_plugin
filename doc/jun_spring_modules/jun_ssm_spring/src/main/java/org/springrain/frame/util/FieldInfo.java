package org.springrain.frame.util;

/**
 * 实体类的字段说明
 * 
 * @author caomei
 *
 */
public class FieldInfo {

    // 字段名称
    private String fieldName = null;

    // 字段类型
    private Class<?> fieldType;

    // 是否是主键
    private Boolean pk = false;

    // 是否是数据库字段
    private Boolean db = true;

    // 是否是lucene全文检索
    private Boolean luceneField = false;
    // 是否tokenized分词字段,只有String作为默认的分词字段,主键强制不分词
    private Boolean stringTokenized = false;

    // 是否进行lucene排序字段,仅支持数值和日期类型
    private Boolean numericSort = false;

    // 字段是否保存,请谨慎修改
    private Boolean luceneStored = true;

    // 字段是否索引,只有索引才能作为查询条件,请谨慎修改
    private Boolean luceneIndex = true;
    
    //FacetField,暂未实现 facet,这样的场景建议换solr了
    //private Boolean luceneFacet=false;

    // wheresql的注解字符串
    private String whereSQL = null;

    // 主键序列
    private String pkSequence = null;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Class<?> getFieldType() {
        return fieldType;
    }

    public void setFieldType(Class<?> fieldType) {
        this.fieldType = fieldType;
    }

    public Boolean getPk() {
        return pk;
    }

    public void setPk(Boolean pk) {
        this.pk = pk;
    }

    public Boolean getDb() {
        return db;
    }

    public void setDb(Boolean db) {
        this.db = db;
    }

    public Boolean getLuceneField() {
        return luceneField;
    }

    public void setLuceneField(Boolean luceneField) {
        this.luceneField = luceneField;
    }

    public Boolean getStringTokenized() {
        return stringTokenized;
    }

    public void setStringTokenized(Boolean stringTokenized) {
        this.stringTokenized = stringTokenized;
    }

    public String getWhereSQL() {
        return whereSQL;
    }

    public void setWhereSQL(String whereSQL) {
        this.whereSQL = whereSQL;
    }

    public String getPkSequence() {
        return pkSequence;
    }

    public void setPkSequence(String pkSequence) {
        this.pkSequence = pkSequence;
    }

    public Boolean getNumericSort() {
        return numericSort;
    }

    public void setNumericSort(Boolean numericSort) {
        this.numericSort = numericSort;
    }

    public Boolean getLuceneStored() {
        return luceneStored;
    }

    public Boolean getLuceneIndex() {
        return luceneIndex;
    }

    public void setLuceneStored(Boolean luceneStored) {
        this.luceneStored = luceneStored;
    }

    public void setLuceneIndex(Boolean luceneIndex) {
        this.luceneIndex = luceneIndex;
    }


}
