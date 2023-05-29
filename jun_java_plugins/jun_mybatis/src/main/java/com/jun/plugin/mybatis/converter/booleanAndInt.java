package com.jun.plugin.mybatis.converter;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author LB
 * @version 1.0
 * @date 2020/12/22 17:14
 */
//将java中的boolean类型转换为数据库中的int类型 true就是1 false就是0
public class booleanAndInt  extends BaseTypeHandler<Boolean>  {
    //set  就是java代码到数据库
    /*
     * ps: preparedStatement 对象
     * I: preparedStatement对象操作参数的位置
     * parameter: java值
     * jdbcType: jdbc操作数据库类型
     */
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Boolean aBoolean, JdbcType jdbcType) throws SQLException {
         if(aBoolean){ //true
             preparedStatement.setInt(i,1);
         }else{
             preparedStatement.setInt(i,0);
         }
    }

    //get 就是从数据库 到java
    public Boolean getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        int sexNum = resultSet.getInt(columnName);
        return sexNum==1?true:false;
    }

    public Boolean getNullableResult(ResultSet resultSet, int index) throws SQLException {
        int sexNum = resultSet.getInt(index);
        return sexNum==1?true:false;
    }

    public Boolean getNullableResult(CallableStatement callableStatement, int index) throws SQLException {
        int sexNum = callableStatement.getInt(index);
        return sexNum==1?true:false;
    }
}
