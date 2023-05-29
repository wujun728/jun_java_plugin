package mybatis.mate.sharding.config;

import mybatis.mate.sharding.ShardingDatasource;
import mybatis.mate.strategy.RandomShardingStrategy;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Invocation;

/**
 * 自定义策略，打开注释 @Component 实现自己的逻辑
 * <p>
 * 默认策略 {@link RandomShardingStrategy}
 * </p>
 */
//@Component
public class MyShardingStrategy extends RandomShardingStrategy {

    /**
     * 决定切换数据源 key {@link ShardingDatasource}
     *
     * @param group          动态数据库组
     * @param invocation     {@link Invocation}
     * @param sqlCommandType {@link SqlCommandType}
     */
    @Override
    public void determineDatasourceKey(String group, Invocation invocation, SqlCommandType sqlCommandType) {
        // 数据源组 group 自定义选择即可， keys 为数据源组内主从多节点，可随机选择或者自己控制
        this.changeDatabaseKey(group, sqlCommandType, keys -> chooseKey(keys, invocation));
    }
}
