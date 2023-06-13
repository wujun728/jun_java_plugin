package cn.wuwenyao.db.doc.generator.dao.impl.dbinfo;

import cn.wuwenyao.db.doc.generator.config.ApplicationConfig;
import cn.wuwenyao.db.doc.generator.dao.DbInfoDao;
import cn.wuwenyao.db.doc.generator.entity.TableInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/***
 * 获取数据库信息的dao实现-抽象基类
 *
 * @author wwy shiqiyue.github.com
 *
 */
public abstract class AbstractDbInfoDao implements DbInfoDao {

    protected JdbcTemplate jdbcTemplate;

    protected ApplicationConfig applicationConfig;


    /***
     * 判断表是否可以生成
     * @param tableName
     * @return
     */
    protected boolean isTableGenerate(String tableName) {
        if (matchWhiteList(tableName)) {
            return true;
        }
        if (matchBlackList(tableName)) {
            return false;
        }
        return true;
    }

    /***
     * 是否匹配白名单
     * @param tableName
     * @return
     */
    private Boolean matchWhiteList(String tableName) {
        List<String> whiteList = applicationConfig.getGenerator().getWhitelist();
        if (CollectionUtils.isNotEmpty(whiteList)) {
            for (String whiteItem : whiteList) {
                if (tableName.matches(whiteItem)) {
                    return true;
                }
            }
        }
        return false;
    }

    /***
     * 是否匹配黑名单
     * @param tableName
     * @return
     */
    private Boolean matchBlackList(String tableName) {
        List<String> blackList = applicationConfig.getGenerator().getBlacklist();
        if (CollectionUtils.isEmpty(blackList)) {
            return false;
        }
        for (String blackItem : blackList) {
            if (tableName.matches(blackItem)) {
                return true;
            }
        }

        return false;
    }


    @Override
    public String databaseName() {
        return null;
    }

    @Override
    public List<TableInfo> tableInfoList() {
        return null;
    }

    @Override
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void setApplicationConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }


}
