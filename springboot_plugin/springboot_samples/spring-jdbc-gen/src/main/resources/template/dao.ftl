package ${packageBase}.dao;

import ${packageBase}.model.${tableBean.tableNameCapitalized};

import java.util.List;

public interface ${tableBean.tableNameCapitalized}${classPrefix}DAO {
/**
* 根据主键查询
* @param id 主键id
* @return 查询对象，无结果时返回null
*/
public ${tableBean.tableNameCapitalized} getByPrimaryKey(Integer id);

/**
* 根据sql查询
* @param whereSql where关键字后面的查询条件，例如：id = ?
* @param params 查询条件中的参数，个数需要与"?"个数保持一致
* @return 查询结果，无结果时返回空记录集
*/
public List<${tableBean.tableNameCapitalized}> queryByConditions(String whereSql, Object [] params);

/**
* 根据sql分页查询, startPos和num必须一起传入
* @param whereSql where关键字后面的查询条件，例如：id = ?
* @param params 查询条件中的参数，个数需要与"?"个数保持一致
* @param startPos 查询起始位置，从0开始
* @param num 查询记录数
* @return 查询结果，无结果时返回空记录集
*/
public List<${tableBean.tableNameCapitalized}> page(String whereSql, Object[] params, int startPos, int num);

/**
* 查询所有记录
* @return 所有记录，无结果时返回空记录集
*/
public List<${tableBean.tableNameCapitalized}> getAll();

/**
* 统计记录数
* @param whereSql where关键字后面的查询条件，例如：id = ?
* @param params 查询条件中的参数，个数需要与"?"个数保持一致
* @return 记录数
*/
public int count(String whereSql, Object[] params);

/**
* 有选择的插入记录，只插入对象中非null值的字段
* @param ${tableBean.tableNameNoDash} 数据对象
* @return 成功插入的记录数
*/
public int insert(${tableBean.tableNameCapitalized} ${tableBean.tableNameNoDash});

/**
* 有选择的插入记录，只插入对象中非null值的字段，并获取主键id到${tableBean.tableNameNoDash}对象中
* @param ${tableBean.tableNameNoDash} 数据对象
* @return 成功插入的记录数
*/
public int save(${tableBean.tableNameCapitalized} ${tableBean.tableNameNoDash});

/**
* 有选择的更新记录，只更新对象中非null值的字段
* @param ${tableBean.tableNameNoDash} 数据对象
* @return 成功更新的记录数
*/
public int change(${tableBean.tableNameCapitalized} ${tableBean.tableNameNoDash});

/**
* 根据主键删除记录
* @param id 主键
* @return 成功删除的记录数
*/
public int removeByPrimaryKey(Integer id);

/**
* 根据sql语句进行删除
* @param whereSql where关键字后面的删除条件，例如：id = ?
* @param params 条件中的参数，个数需要与"?"个数保持一致
* @return 成功删除的记录数
*/
public int removeByConditions(String whereSql, Object[] params);
}


