package io.github.wujun728.common;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JdbcTemplateUtils {



    @Autowired
    private JdbcTemplate jdbcTemplate;


    public  <T> void batchInsert(List<T> lists, Class<T> clazz){
//        StringBuilder sql = new StringBuilder("INSERT INTO ");
        TableInfo tableInfo = TableInfoHelper.getTableInfo(clazz);
        //表名
//        sql.append(tableInfo.getTableName());
//
        //获取排除主键后的字段
        List<TableFieldInfo> tableFieldInfos = tableInfo
                .getFieldList()
                .stream()
                .filter(tableFieldInfo -> !tableFieldInfo.getColumn().equals(tableInfo.getKeyColumn()))
                .collect(Collectors.toList());
//
//        String columns = tableFieldInfos.stream()
//                .map(TableFieldInfo::getColumn)
//                .collect(Collectors.joining(",","(",")"));
//
//        sql.append(columns);
//        String values = tableFieldInfos.stream()
//                .map(tableFieldInfo -> "?")
//                .collect(Collectors.joining(",","(",")"));
//        sql.append(" values " + values);
//        log.info("sql : {} ",sql.toString());
        String sql = getSql(tableInfo,tableFieldInfos);


        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                T t = lists.get(i);
//                ps.setString(1, user.getName());
//                ps.setString(2, user.getEmail());
                setParams(ps,t,tableFieldInfos,tableInfo);
            }

            @Override
            public int getBatchSize() {
                return lists.size();
            }
        });
    }

    @SneakyThrows
    public <T> void setParams(PreparedStatement ps, T t, List<TableFieldInfo> tableFieldInfos, TableInfo tableInfo){
        //获取mybatis 的Configuration
        Configuration configuration = tableInfo.getConfiguration();

        //获取mybatis 注册的TypeHandler
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();

        //通过mybatis 反射工具获取反射对象
        MetaObject metaObject = MetaObject.forObject(t, new DefaultObjectFactory(),
                new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());

        //通过java 实体对象设置jdbc参数
        for (int i = 0; i < tableFieldInfos.size(); i++) {
            //获取字段对象
            TableFieldInfo tableFieldInfo = tableFieldInfos.get(i);

            //获取字段的typeHandler
            TypeHandler typeHandler = typeHandlerRegistry.getTypeHandler(tableFieldInfo.getPropertyType());

            //获取该字段的值
            Object value =  metaObject.getValue(tableFieldInfo.getProperty());

            //如果字段值为空且jdbcType也为空就获取默认的jdbcType
            JdbcType jdbcType = Objects.isNull(value) && Objects.isNull(tableFieldInfo.getJdbcType()) ? configuration.getJdbcTypeForNull() : tableFieldInfo.getJdbcType();

            //通过typeHandler 设置参数
            typeHandler.setParameter(ps,i+1,value,jdbcType);
        }

    }

    /***
     * 获取sql
     * @param tableInfo
     * @param <T>
     * @return
     */
    public <T> String getSql(TableInfo tableInfo, List<TableFieldInfo> tableFieldInfos ){
        //sql
        StringBuilder sql = new StringBuilder("INSERT INTO ");

        //表名
        sql.append(tableInfo.getTableName());

        //添加字段
        String columns = tableFieldInfos.stream()
                .map(TableFieldInfo::getColumn)
                .collect(Collectors.joining(",", "(", ")"));

        sql.append(columns);

        //添加values
        sql.append(" values ");

        //添加参数?
        String params = tableFieldInfos
                .stream()
                .map(tableFieldInfo -> "?")
                .collect(Collectors.joining(",", "(", ")"));

        sql.append(params);

        return sql.toString();
    }

}

