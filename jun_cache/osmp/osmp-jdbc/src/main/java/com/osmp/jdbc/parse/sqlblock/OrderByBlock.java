/*   
 * Project: OSMP
 * FileName: OrderByBlock.java
 * version: V1.0
 */
package com.osmp.jdbc.parse.sqlblock;

import java.util.Map;

/**
 * orderby块
 * @author heyu
 *
 */
public class OrderByBlock implements SQLBlock{

    //排序字段
    private String orderColumn;
    //排序方式变量(asc|desc)
    private String orderTypeVar;
    //排序方式常量(asc|desc)
    private String orderType;
    
    public OrderByBlock(String orderColumn) {
        this.orderColumn = orderColumn;
        this.orderType = "desc";
    }

    public OrderByBlock(String orderColumn, String orderTypeVar) {
        this.orderColumn = orderColumn;
        this.orderTypeVar = orderTypeVar;
        this.orderType = "desc";
    }
    
    public OrderByBlock(String orderColumn, String orderTypeVar, String orderType) {
        this.orderColumn = orderColumn;
        this.orderTypeVar = orderTypeVar;
        this.orderType = "asc".equals(orderType) ? "asc" : "desc";
    }

    @Override
    public String sql(Map<String, Object> params) {
        if(params == null) return "";
        if(orderColumn == null || "".equals(orderColumn)) return "";
        Object orderColumnName = params.get(orderColumn);
        if(orderColumnName == null || "".equals(orderColumnName)) return "";
        StringBuffer sb = new StringBuffer();
        sb.append(orderColumnName.toString());
        sb.append(" ");
        if(orderTypeVar != null && !"".equals(orderTypeVar)){
            Object orderTypeName = params.get(orderTypeVar);
            if(orderTypeName != null && !"".equals(orderTypeName)){
                sb.append(("asc".equals(orderTypeName.toString()))?"asc":"desc");
            }else{
                sb.append("desc");
            }
        }else{
            sb.append(orderType);
        }
        return sb.toString();
    }
    
}
