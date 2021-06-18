package com.ibeetl.admin.console.web.query;

import com.ibeetl.admin.core.annotation.Query;
import com.ibeetl.admin.core.util.enums.CoreDictType;
import com.ibeetl.admin.core.web.query.PageParam;
import java.util.Date;
/**
 *CoreDict查询
 */
public class CoreDictQuery extends PageParam {
    @Query(name = "字典值", display = true)
    private String value;
    @Query(name = "字典名称", display = true)
    private String name;
    @Query(name = "字典类型名称", display = true)
    private String typeName;
    @Query(name = "父字典", display = true)
    private String parent;
    public String getValue(){
        return  value;
    }
    public void setValue(String value ){
        this.value = value;
    }
    public String getName(){
        return  name;
    }
    public void setName(String name ){
        this.name = name;
    }
    public String getTypeName(){
        return  typeName;
    }
    public void setTypeName(String typeName ){
        this.typeName = typeName;
    }
    public String getParent(){
        return  parent;
    }
    public void setParent(String parent ){
        this.parent = parent;
    }
 
}
