package com.zhaodui.springboot.system.mapper;

import com.zhaodui.springboot.common.mapper.BaseMapper;
import com.zhaodui.springboot.common.mdoel.DictModel;
import com.zhaodui.springboot.system.model.DuplicateCheckVo;
import com.zhaodui.springboot.system.model.SysDict;
import com.zhaodui.springboot.system.model.TreeSelectModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface SysDictMapper extends BaseMapper<SysDict> {
    /**
     *  重复检查SQL
     * @return
     */
    public long duplicateCheckCountSql(DuplicateCheckVo duplicateCheckVo);
    public long duplicateCheckCountSqlNoDataId(DuplicateCheckVo duplicateCheckVo);

    public List<DictModel> queryDictItemsByCode(@Param("code") String code);
    public List<DictModel> queryTableDictItemsByCode(@Param("table") String table,@Param("text") String text,@Param("code") String code);
    public List<DictModel> queryTableDictItemsByCodeAndFilter(@Param("table") String table,@Param("text") String text,@Param("code") String code,@Param("filterSql") String filterSql);


    public String queryDictTextByKey(@Param("code") String code,@Param("key") String key);

    public String queryTableDictTextByKey(@Param("table") String table,@Param("text") String text,@Param("code") String code,@Param("key") String key);

    public List<DictModel> queryTableDictByKeys(@Param("table") String table, @Param("text") String text, @Param("code") String code, @Param("keyArray") String[] keyArray);

    /**
     * 查询所有部门 作为字典信息 id -->value,departName -->text
     * @return
     */
    public List<DictModel> queryAllDepartBackDictModel();

    /**
     * 查询所有用户  作为字典信息 username -->value,realname -->text
     * @return
     */
    public List<DictModel> queryAllUserBackDictModel();

    /**
     * 通过关键字查询出字典表
     * @param table
     * @param text
     * @param code
     * @param keyword
     * @return
     */
    public List<DictModel> queryTableDictItems(@Param("table") String table, @Param("text") String text, @Param("code") String code, @Param("keyword") String keyword);

    /**
     * 根据表名、显示字段名、存储字段名 查询树
     * @param table
     * @param text
     * @param code
     * @param pid
     * @param hasChildField
     * @return
     */
    List<TreeSelectModel> queryTreeList(@Param("query") Map<String, String> query, @Param("table") String table, @Param("text") String text, @Param("code") String code, @Param("pidField") String pidField, @Param("pid") String pid, @Param("hasChildField") String hasChildField);

    /**
     * 删除
     * @param id
     */
    @Select("delete from sys_dict where id = #{id}")
    public void deleteOneById(@Param("id") String id);

    /**
     * 查询被逻辑删除的数据
     * @return
     */
    @Select("select * from sys_dict where del_flag = 1")
    public List<SysDict> queryDeleteList();

    /**
     * 修改状态值
     * @param delFlag
     * @param id
     */
    @Update("update sys_dict set del_flag = #{flag,jdbcType=INTEGER} where id = #{id,jdbcType=VARCHAR}")
    public void updateDictDelFlag(@Param("flag") int delFlag, @Param("id") String id);
}