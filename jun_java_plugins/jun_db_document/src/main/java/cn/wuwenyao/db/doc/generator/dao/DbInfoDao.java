package cn.wuwenyao.db.doc.generator.dao;

import cn.wuwenyao.db.doc.generator.config.ApplicationConfig;
import cn.wuwenyao.db.doc.generator.entity.TableInfo;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/***
 * 获取数据库信息的dao接口
 *
 * @author wwy
 *
 */
public interface DbInfoDao {

    /****
     * 获取数据库名称
     *
     * @return
     */
    public String databaseName();

    /***
     * 获取表的信息
     *
     * @return
     */
    public List<TableInfo> tableInfoList();

    /***
     * 设置jdbcTemplate
     *
     * @param jdbcTemplate
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate);

    /***
     * 设置配置信息
     * @param applicationConfig
     */
    public void setApplicationConfig(ApplicationConfig applicationConfig);

}
