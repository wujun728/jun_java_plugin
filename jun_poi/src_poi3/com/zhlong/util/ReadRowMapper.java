/**
 * ExcelReadRowMapper.java类：本类位于insurance项目中的
 * com.magonchina.util包路径下，
 * 类具体的作用请参看代码中的文档注释。
 * 
 * Created By 赵海龙
 */
package com.zhlong.util;

import java.util.Map;

import org.apache.poi.ss.usermodel.Row;

/**.
 * @author 赵海龙
 * @version 1.0
 * @since 2016年5月13日 下午5:26:57
 */
public interface ReadRowMapper<T> {
    /**.
     * 方法：在导入excel时，用于自定义处理excel中的每行数据,调用者实现
     * @param row excel中的行数据
     */
    T rowMap(Row row,Map<String, Object> map);
}
