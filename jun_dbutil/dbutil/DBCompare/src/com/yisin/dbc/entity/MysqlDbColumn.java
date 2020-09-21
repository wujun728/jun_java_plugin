package com.yisin.dbc.entity;

/**
 * mysql数据库表字段信息
 * 
 * @author Yisin
 *
 */
public class MysqlDbColumn extends DbTableColumn {
    private String tableSchema; // 所属库
    private String tableName; // 所属表
    private String columnName; // 列名
    private String columnType; // bigint(11)，varchar(30)
    private String dataType; // bigint->Long，int->Integer，varchar/char->String，timestamp->Timestamp，date/time->Date，float->Float，double/decimal->Double，blob->byte[]
    private Object columnDefault; // 列缺省值
    private Long characterOctetLength; // 列最大字节长度，utf-8：
                                          // characterMaximumLength*
                                          // 3，gbk：characterMaximumLength* 2
    private Long characterMaximumLength; // 列最大长度
    private Integer ordinalPosition; // 字节顺序码，从1开始
    private String isNullable; // 是否可为空，YES：可空，NO：非空
    private String columnKey; // 列键值,PRI主键
    private String extra; // auto_increment 自增长
    private String columnComment; // 列描述
    private String privileges; // 列权限集合，以逗号隔开

    public String getTableSchema() {
        return tableSchema;
    }

    public MysqlDbColumn setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public MysqlDbColumn setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String getColumnName() {
        return columnName;
    }

    public MysqlDbColumn setColumnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public String getColumnType() {
        return columnType;
    }

    public MysqlDbColumn setColumnType(String columnType) {
        this.columnType = columnType;
        return this;
    }

    public String getDataType() {
        return dataType;
    }

    public MysqlDbColumn setDataType(String dataType) {
        if(dataType != null){
            this.dataType = dataType.toUpperCase();
            if(this.dataType.equals("INT")){
                this.dataType = "INTEGER";
            } else if(this.dataType.equals("DATETIME")){
                this.dataType = "TIMESTAMP";
            }
        }
        return this;
    }

    public Object getColumnDefault() {
        return columnDefault;
    }

    public MysqlDbColumn setColumnDefault(Object columnDefault) {
        this.columnDefault = columnDefault;
        return this;
    }

    public Long getCharacterOctetLength() {
        return characterOctetLength;
    }

    public MysqlDbColumn setCharacterOctetLength(Long characterOctetLength) {
        this.characterOctetLength = characterOctetLength;
        return this;
    }

    public Long getCharacterMaximumLength() {
        return characterMaximumLength;
    }

    public MysqlDbColumn setCharacterMaximumLength(Long characterMaximumLength) {
        this.characterMaximumLength = characterMaximumLength;
        return this;
    }

    public Integer getOrdinalPosition() {
        return ordinalPosition;
    }

    public MysqlDbColumn setOrdinalPosition(Integer ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
        return this;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public MysqlDbColumn setIsNullable(String isNullable) {
        this.isNullable = isNullable;
        return this;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public MysqlDbColumn setColumnKey(String columnKey) {
        this.columnKey = columnKey;
        return this;
    }

    public String getExtra() {
        return extra;
    }

    public MysqlDbColumn setExtra(String extra) {
        this.extra = extra;
        return this;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public MysqlDbColumn setColumnComment(String columnComment) {
        this.columnComment = columnComment;
        return this;
    }

    public String getPrivileges() {
        return privileges;
    }

    public MysqlDbColumn setPrivileges(String privileges) {
        this.privileges = privileges;
        return this;
    }

    public boolean isPrimaryKey(){
        return columnKey != null && columnKey.equalsIgnoreCase("PRI");
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getClass().getName());
        sb.append(" # tableSchema=" + (tableSchema == null ? "null" : tableSchema.toString()));
        sb.append("; tableName=" + (tableName == null ? "null" : tableName.toString()));
        sb.append("; columnName=" + (columnName == null ? "null" : columnName.toString()));
        sb.append("; columnType=" + (columnType == null ? "null" : columnType.toString()));
        sb.append("; dataType=" + (dataType == null ? "null" : dataType.toString()));
        sb.append("; characterMaximumLength=" + (characterMaximumLength == null ? "null" : characterMaximumLength.toString()));
        sb.append("; characterOctetLength=" + (characterOctetLength == null ? "null" : characterOctetLength.toString()));
        sb.append("; columnDefault=" + (columnDefault == null ? "null" : columnDefault.toString()));
        sb.append("; ordinalPosition=" + (ordinalPosition == null ? "null" : ordinalPosition.toString()));
        sb.append("; isNullable=" + (isNullable == null ? "null" : isNullable.toString()));
        sb.append("; columnKey=" + (columnKey == null ? "null" : columnKey.toString()));
        sb.append("; extra=" + (extra == null ? "null" : extra.toString()));
        sb.append("; columnComment=" + (columnComment == null ? "null" : columnComment.toString()));
        sb.append("; privileges=" + (privileges == null ? "null" : privileges.toString()));
        return sb.toString();
    }
    
}
