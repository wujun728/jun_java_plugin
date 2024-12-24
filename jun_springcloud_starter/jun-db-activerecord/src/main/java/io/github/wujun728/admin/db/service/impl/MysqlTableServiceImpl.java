package io.github.wujun728.admin.db.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import io.github.wujun728.admin.common.PageData;
import io.github.wujun728.admin.common.PageParam;
import io.github.wujun728.admin.common.Result;
import io.github.wujun728.admin.common.service.impl.AbstractCacheService;
import io.github.wujun728.admin.db.config.DbConfig;
import io.github.wujun728.admin.db.data.*;
import io.github.wujun728.admin.db.service.JdbcDao;
import io.github.wujun728.admin.db.service.TableService;
import io.github.wujun728.admin.util.StringUtil;
import io.github.wujun728.admin.util.TemplateUtil;
import io.github.wujun728.admin.db.data.ColumnInfo;
import io.github.wujun728.admin.db.data.ForeignKey;
import io.github.wujun728.admin.db.data.IndexInfo;
import io.github.wujun728.admin.db.data.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service("tableService")
//@ConditionalOnProperty(value="db.type",havingValue = "mysql")
@Slf4j
public class MysqlTableServiceImpl extends AbstractCacheService<Result<TableInfo>> implements TableService {
    @Resource
    private DbConfig dbConfig;

    @Resource
    private JdbcDao jdbcDao;

    @Override
    protected boolean isNull(Result<TableInfo> value) {
        return value == null || value.getData() == null;
    }

    @Override
    public Result<PageData<TableInfo>> queryTable(PageParam pageParam) {

        String sql = getTableSql();
        List<Object> args = new ArrayList<>();
        if(StrUtil.isNotBlank(pageParam.getStr("tableName"))){
            sql += " and table_name like ? ";
            args.add(StrUtil.format("%{}%",pageParam.getStr("tableName")));
        }

        if(StrUtil.isNotBlank(pageParam.getStr("tableComment"))){
            sql += " and table_comment like ? ";
            args.add(StrUtil.format("%{}%",pageParam.getStr("tableComment")));
        }
        sql += " order by table_name asc ";
        Object[] params = args.toArray();

        Result<PageData<TableInfo>> result = jdbcDao.query(pageParam, TableInfo.class, sql, params);
        List<TableInfo> tableInfos = result.getData().getItems();
        tableInfos.forEach(tableInfo -> {
            tableInfo.setId(tableInfo.getTableName());
            tableInfo.setOldTableName(tableInfo.getTableName());
        });
        return result;
    }

    private String getTableSql(){
        //排除工作流表
        return "select t.TABLE_NAME,t.TABLE_COMMENT,t.TABLE_ROWS from "+dbConfig.getManageSchema()+".`TABLES` t where t.TABLE_SCHEMA = '"+dbConfig.getSchema()+"' and t.table_name not like 'act_%'";
    }

    @Override
    protected Result<TableInfo> load(String key) {
        return tableInfo(key);
    }

    @Override
    public Result<TableInfo> tableInfo(String tableName) {
        if(StrUtil.isBlank(tableName)){
            return Result.success(new TableInfo());
        }

        String tableSql = getTableSql() + " and table_name='"+tableName+"'";
        List<TableInfo> list = jdbcDao.find(tableSql,TableInfo.class);
        if(list.isEmpty()){
            return Result.error("表不存在");
        }
        TableInfo tableInfo = list.get(0);
        tableInfo.setOldTableName(tableInfo.getTableName());
        tableInfo.setId(tableInfo.getTableName());

        String columnSql = "select c.COLUMN_NAME,c.COLUMN_COMMENT,c.COLUMN_TYPE,c.IS_NULLABLE from "+dbConfig.getManageSchema()+".`COLUMNS` c where c.TABLE_SCHEMA = '"+dbConfig.getSchema()+"' and c.TABLE_NAME = '"+tableName+"' and c.column_name <> 'id' ";
        List<ColumnInfo> columnInfos = jdbcDao.find(columnSql, ColumnInfo.class);
        for(ColumnInfo c:columnInfos){
            c.setOldColumnName(c.getColumnName());
        }
        tableInfo.setColumnInfos(columnInfos);

        String indexSql = "show keys from "+tableName;
        List<Map<String, Object>> indexList = jdbcDao.find(indexSql);

        String foreignKeySql = "select\n" +
                "    s.CONSTRAINT_NAME OLD_CONSTRAINT_NAME,\n" +
                "    s.CONSTRAINT_NAME,\n" +
                "    s.COLUMN_NAME,\n" +
                "    s.REFERENCED_TABLE_NAME,\n" +
                "    s.REFERENCED_COLUMN_NAME,\n" +
                "    rc.COLUMN_COMMENT REFERENCED_COLUMN_COMMENT\n" +
                "from INFORMATION_SCHEMA.KEY_COLUMN_USAGE s\n" +
                "         left join information_schema.`TABLES` t on t.TABLE_NAME = s.TABLE_NAME and t.TABLE_SCHEMA = s.TABLE_SCHEMA\n" +
                "         left join information_schema.`COLUMNS` tc on tc.TABLE_NAME = s.TABLE_NAME and tc.TABLE_SCHEMA = s.TABLE_SCHEMA and tc.COLUMN_NAME = s.COLUMN_NAME\n" +
                "         left join information_schema.`TABLES` r on r.TABLE_NAME = s.REFERENCED_TABLE_NAME and r.TABLE_SCHEMA = s.TABLE_SCHEMA\n" +
                "         left join information_schema.`COLUMNS` rc on rc.TABLE_NAME = s.REFERENCED_TABLE_NAME and rc.TABLE_SCHEMA = s.TABLE_SCHEMA and rc.COLUMN_NAME = s.REFERENCED_COLUMN_NAME\n" +
                "where s.table_schema ='${schema}'\n" +
                "  and s.REFERENCED_COLUMN_NAME is not null\n" +
                "  and upper(t.TABLE_NAME) = upper(?)";
        foreignKeySql = TemplateUtil.getValue(foreignKeySql, MapUtil.builder("schema", dbConfig.getSchema()).map());
//        String foreignKeySql = StrUtil.format("select\n" +
//                "s.CONSTRAINT_NAME\n" +
//                "from {}.KEY_COLUMN_USAGE s\n" +
//                "where s.TABLE_SCHEMA = '{}'\n" +
//                "and s.TABLE_NAME = '{}'\n" +
//                "and s.CONSTRAINT_SCHEMA <> 'PRIMARY'"
//                ,dbConfig.getManageSchema()
//                ,dbConfig.getSchema(),
//                tableName);
        List<ForeignKey> foreignKeys_ = jdbcDao.find(foreignKeySql,ForeignKey.class,tableInfo.getTableName());
        Set<String> foreignKeys = new HashSet<>();
        foreignKeys_.forEach(item->{
            foreignKeys.add(item.getConstraintName());
        });
        tableInfo.setForeignKeys(foreignKeys_);

        List<IndexInfo> indexInfos = new ArrayList<>();
        Map<String,IndexInfo> indexInfoMap = new HashMap<>();

        Collections.sort(indexList, (o1, o2) -> {
            String keyName1 = (String) o1.get("keyName");
            String keyName2 = (String) o2.get("keyName");
            Long seqIndex1 = (Long) o1.get("seqInIndex");
            Long seqIndex2 = (Long) o2.get("seqInIndex");

            int compare = keyName1.compareTo(keyName2);
            compare = compare == 0 ? seqIndex1.compareTo(seqIndex2) : compare;
            return compare;
        });
        for(Map<String,Object> index:indexList){
            String keyName = (String) index.get("keyName");
            String columnName = (String) index.get("columnName");
            String indexComment = (String) index.get("indexComment");
            Long nonUnique = Long.valueOf(index.get("nonUnique").toString());
            //排除唯一索引和主键
            if(Long.valueOf(0).equals(nonUnique)){
                continue;
            }
            //排除外键
            if(foreignKeys.contains(keyName)){
                continue;
            }
            IndexInfo indexInfo = indexInfoMap.get(keyName);
            if(indexInfo == null){
                indexInfo = new IndexInfo();
                indexInfo.setKeyName(keyName);
                indexInfo.setIndexComment(indexComment);
                indexInfo.setColumnName(columnName);
                indexInfo.setOldKeyName(keyName);

                indexInfoMap.put(keyName,indexInfo);
                indexInfos.add(indexInfo);
            }else{
                indexInfo.setColumnName(indexInfo.getColumnName()+","+columnName);
            }
        }
        tableInfo.setIndexInfos(indexInfos);

        return new Result<>(tableInfo);
    }

    @Override
    public Result<Void> updateTable(TableInfo tableInfo) {

        boolean isCreate = StrUtil.isBlank(tableInfo.getOldTableName());
        if(isCreate){
            //建表
            String sql = "create table "+tableInfo.getTableName()+" (\n";
            sql += "\tid BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键' ,\n";
            for (int i = 0; i < tableInfo.getColumnInfos().size(); i++) {
                ColumnInfo columnInfo = tableInfo.getColumnInfos().get(i);
                sql += "\t";
                if(columnInfo.getColumnName().equalsIgnoreCase("id")){
                    continue;
                }else{
                    sql += columnInfo.getColumnName()+" "+columnInfo.getColumnType() + (!"YES".equalsIgnoreCase(columnInfo.getIsNullable()) ? " NOT NULL" : "") + " comment '"+columnInfo.getColumnComment()+"'";
                }
                sql += i == tableInfo.getColumnInfos().size() -1 ? "\n" : ",\n";
            }
            sql += ") comment = '"+tableInfo.getTableComment()+"'";
            jdbcDao.update("建表",sql);

            tableInfo.getIndexInfos().forEach(indexInfo -> {
                String indexSql = StrUtil.format(" alter table {} add index {} ({}) comment '{}' ",
                        tableInfo.getTableName(),
                        indexInfo.getKeyName(),
                        indexInfo.getColumnName(),
                        indexInfo.getIndexComment()
                );
                jdbcDao.update("添加索引",indexSql);
            });

            tableInfo.getForeignKeys().forEach(foreignKey -> {
                //增加外键
                String foreignKeySql = StrUtil.format("alter table {} " +
                                "add constraint {}  " +
                                "foreign key({}) " +
                                "REFERENCES {}({}) " +
                                "ON DELETE CASCADE " +
                                "ON UPDATE CASCADE",
                        tableInfo.getTableName(),
                        foreignKey.getConstraintName(),
                        foreignKey.getColumnName(),
                        foreignKey.getReferencedTableName(),
                        foreignKey.getReferencedColumnName());
                jdbcDao.update("增加外键",foreignKeySql);
            });

            return Result.success();
        }

        //更新表
        Result<TableInfo> oldTable = this.get(tableInfo.getOldTableName());
        if(!oldTable.isSuccess()){
            return Result.error("表不存在");
        }
        TableInfo oldTableInfo = oldTable.getData();
        if(!oldTableInfo.getOldTableName().equals(tableInfo.getTableName())){
            String sql = StrUtil.format("alter table {} rename to {} ",oldTableInfo.getTableName(),tableInfo.getTableName());
            jdbcDao.update("修改表名",sql);
        }
        if(!oldTableInfo.getTableComment().equals(tableInfo.getTableComment())){
            String sql = StrUtil.format("alter table {} comment ? ",tableInfo.getTableName());
            jdbcDao.update("修改表注释",sql,tableInfo.getTableComment());
        }

        //更新字段
        this.updateColumns(tableInfo,oldTableInfo);

        //更新索引
        this.updateIndex(tableInfo,oldTableInfo);

        //更新外键
        this.updateForeignKeys(tableInfo,oldTableInfo);

        super.invalid(tableInfo.getTableName());
        return Result.success();
    }

    //更新列
    private void updateColumns(TableInfo tableInfo,TableInfo oldTableInfo){
        Map<String, ColumnInfo> oldColumnMap = oldTableInfo.getColumnInfos().stream().collect(Collectors.toMap(ColumnInfo::getColumnName, c -> c));
        Set<String> names = new HashSet<>();
        //新增/修改字段
        for(ColumnInfo columnInfo:tableInfo.getColumnInfos()){
            if(StrUtil.isBlank(columnInfo.getOldColumnName())){
                //新增字段
                String sql = StrUtil.format("alter table {} add {} {} {} comment '{}' ",
                        tableInfo.getTableName(),
                        columnInfo.getColumnName(),
                        columnInfo.getColumnType(),
                        (!"YES".equalsIgnoreCase(columnInfo.getIsNullable()) ? " NOT NULL" : ""),
                        columnInfo.getColumnComment()
                );
                jdbcDao.update("新增字段",sql);
            }else{
                names.add(columnInfo.getOldColumnName());
                ColumnInfo oldColumnInfo = oldColumnMap.get(columnInfo.getOldColumnName());
                if(!oldColumnInfo.equals(columnInfo)){
                    if(!columnInfo.getOldColumnName().equalsIgnoreCase(columnInfo.getColumnName())){
                        //修改字段名称
                        String sql = StrUtil.format(" alter table {} change {} {} {} comment '{}'",
                                tableInfo.getTableName(),
                                columnInfo.getOldColumnName(),
                                columnInfo.getColumnName(),
                                columnInfo.getColumnType(),
                                columnInfo.getColumnComment()
                        );
                        jdbcDao.update("修改字段名称",sql);
                    }else{
                        //修改字段
                        String sql = StrUtil.format("alter table {} modify {} {} {} comment '{}' ",
                                tableInfo.getTableName(),
                                columnInfo.getColumnName(),
                                columnInfo.getColumnType(),
                                (!"YES".equalsIgnoreCase(columnInfo.getIsNullable()) ? " NOT NULL" : ""),
                                columnInfo.getColumnComment()
                        );
                        jdbcDao.update("修改字段",sql);
                    }
                }
            }
        }
        //删除字段
        for(ColumnInfo oldColumnInfo:oldTableInfo.getColumnInfos()){
            if(!names.contains(oldColumnInfo.getColumnName())){
                String sql = StrUtil.format("alter table {} drop column {} ",tableInfo.getTableName(),oldColumnInfo.getColumnName());
                jdbcDao.update("删除字段",sql);
            }
        }

    }

    //更新外键
    private void updateIndex(TableInfo tableInfo,TableInfo oldTableInfo){
        Map<String, IndexInfo> oldIndexMap = oldTableInfo.getIndexInfos().stream().collect(Collectors.toMap(IndexInfo::getKeyName, c -> c));

        Set<String> indexNames = new HashSet<>();
        //新增/修改索引
        for(IndexInfo indexInfo:tableInfo.getIndexInfos()){

            boolean newIndex = false;
            if(StrUtil.isBlank(indexInfo.getOldKeyName())){
                newIndex = true;
            }else{
                indexNames.add(indexInfo.getOldKeyName());
                IndexInfo oldIndexInfo = oldIndexMap.get(indexInfo.getOldKeyName());
                if(!oldIndexInfo.equals(indexInfo)){
                    //修改索引,先删除,后添加
                    String sql = StrUtil.format(" drop index {} on {} ",oldIndexInfo.getKeyName(),tableInfo.getTableName());
                    jdbcDao.update("删除索引",sql);
                    newIndex = true;
                }
            }
            if(newIndex){
                //新增索引
                String indexSql = StrUtil.format(" alter table {} add index {} ({}) comment '{}' ",
                        tableInfo.getTableName(),
                        indexInfo.getKeyName(),
                        indexInfo.getColumnName(),
                        indexInfo.getIndexComment()
                );
                jdbcDao.update("添加索引",indexSql);
            }
        }
        //删除索引
        for(IndexInfo oldIndexInfo:oldTableInfo.getIndexInfos()){
            if(!indexNames.contains(oldIndexInfo.getKeyName())){
                String sql = StrUtil.format(" drop index {} on {} ",oldIndexInfo.getKeyName(),tableInfo.getTableName());
                jdbcDao.update("删除索引",sql);
            }
        }
    }
    //更新外键
    private void updateForeignKeys(TableInfo tableInfo,TableInfo oldTableInfo){
        Map<String, ForeignKey> oldForeignKeyMap = oldTableInfo.getForeignKeys().stream().collect(Collectors.toMap(ForeignKey::getConstraintName, c -> c));

        Set<String> foreignKeyNames = new HashSet<>();
        //新增/修改索引
        for(ForeignKey foreignKey:tableInfo.getForeignKeys()){

            boolean newFk = false;
            if(StrUtil.isBlank(foreignKey.getOldConstraintName())){
                newFk = true;
            }else{
                foreignKeyNames.add(foreignKey.getOldConstraintName());
                ForeignKey oldForeignKey = oldForeignKeyMap.get(foreignKey.getOldConstraintName());
                if(!oldForeignKey.equals(foreignKey)){
                    //修改索引,先删除,后添加
                    String sql = StrUtil.format("alter table {} drop foreign key {}",tableInfo.getTableName(),oldForeignKey.getOldConstraintName());
                    jdbcDao.update("删除外键",sql);
                    newFk = true;
                }
            }
            if(newFk){
                //增加外键
                String sql = StrUtil.format("alter table {} " +
                                "add constraint {}  " +
                                "foreign key({}) " +
                                "REFERENCES {}({}) " +
                                "ON DELETE CASCADE " +
                                "ON UPDATE CASCADE",
                        tableInfo.getTableName(),
                        foreignKey.getConstraintName(),
                        foreignKey.getColumnName(),
                        foreignKey.getReferencedTableName(),
                        foreignKey.getReferencedColumnName());
                jdbcDao.update("增加外键",sql);
            }
        }
        //删除外键
        for(ForeignKey oldForeignKey:oldTableInfo.getForeignKeys()){
            if(!foreignKeyNames.contains(oldForeignKey.getOldConstraintName())){
                String sql = StrUtil.format("alter table {} drop foreign key {}",tableInfo.getTableName(),oldForeignKey.getOldConstraintName());
                jdbcDao.update("删除外键",sql);
            }
        }
    }

    @Override
    public Result dropTable(String tableName) {
        String sql = StrUtil.format(" drop table {}", tableName);
        jdbcDao.update("删除表",sql);
        super.invalid(tableName);
        return Result.success();
    }

    @Override
    protected String getFullKey(String key) {
        return StrUtil.format("tableInfo:{}",key.toUpperCase());
    }

    @Override
    public Map<String, String> generateCode(String tableName) {
        Result<TableInfo> result = this.get(tableName);
        TableInfo tableInfo = result.getData();
        List<ColumnInfo> columnInfos = tableInfo.getColumnInfos();
        Map<String,String> java = new HashMap<>();

        Map<String,Object> params = new HashMap<>();
        params.put("tableName", StringUtil.toFirstUp(StringUtil.toFieldColumn(tableInfo.getTableName())));
        params.put("tableComment",tableInfo.getTableComment());
        params.put("date", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

        List<Map<String,Object>> columns = new ArrayList<>();
        String exportSql = "-- 导出" + tableInfo.getTableComment();
        String querySql = "-- 查询" + tableInfo.getTableComment();
        querySql += "\nselect";
        exportSql += "\nselect";

        String alis = StringUtil.getAlis(tableName);
        querySql += "\n\t"+alis+".id, -- 主键";
        exportSql += "\n\t"+alis+".id,-- 主键";
        String lastColumnName = columnInfos.get(columnInfos.size() - 1).getColumnName();
        for(ColumnInfo columnInfo:columnInfos){
            Map<String,Object> column = new HashMap<>();
            column.put("columnName",StringUtil.toFieldColumn(columnInfo.getColumnName()));
            column.put("columnComment",columnInfo.getColumnComment());
            String type = columnInfo.getColumnType();
            String javaType = "";
            if(type.startsWith("int")){
                javaType = "Integer";
            }else if(type.startsWith("bigint")){
                javaType = "Long";
            }else if(type.startsWith("varchar") || type.startsWith("longtext")){
                javaType = "String";
            }else if(type.startsWith("datetime")){
                javaType = "Date";
            }else if(type.startsWith("float")){
                javaType = "Double";
            }
            column.put("type",javaType);
            columns.add(column);

            String d = ",";
            if(lastColumnName.equals(columnInfo.getColumnName())){
                d="";
            }
            querySql += "\n\t"+alis+"."+columnInfo.getColumnName()+d+" -- " + columnInfo.getColumnComment();
            exportSql += "\n\t"+alis+"."+columnInfo.getColumnName()+" " + columnInfo.getColumnComment()+d;
        }
        querySql += "\nfrom "+tableInfo.getTableName() + " " + alis;
        exportSql += "\nfrom "+tableInfo.getTableName() + " " + alis;
        params.put("columns",columns);

        String model = TemplateUtil.getUi("model.java.vm", params);

        java.put("model",model);
        java.put("querySql",querySql);
        java.put("exportSql",exportSql);
        return java;
    }
}
